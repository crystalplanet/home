var version = 'v1::';

self.addEventListener('install', function (event) {
	event.waitUntil(
		caches
			.open(version + 'static')
			.then(function (cache) {
				return cache.addAll([
					'/',
					'/css/less.css',
					'/fonts/GalanoGrotesqueBold.otf',
					'/fonts/GalanoGrotesqueLight.otf',
					'/js/app.js',
				]);
			})
			.then(function () {
				console.log('Worker Installed');
			})
	);
});

self.addEventListener('activate', function (event) {
	event.waitUntil(
		caches
			.keys()
			.then(function (keys) {
				return Promise.all(
					keys
						.filter(function (key) {
							return !key.startsWith(version);
						})
						.map(function (key) {
							return caches.delete(key);
						})
				);
			})
			.then(function() {
				console.log('Worker Updated');
			})
	);
});

self.addEventListener('fetch', function (event) {
	// Ignore all but 'GET' requests
	if (event.request.method !== 'GET') {
		return;
	}

	event.respondWith(
		caches
			.match(event.request)
			.then(function (cached) {
				var networked = fetch(event.request)
					.then(fetchedFromNetwork, unableToResolve)
					.catch(unableToResolve);

				return cached || networked;

				function fetchedFromNetwork(response) {
					var cacheCopy = response.clone();

					caches
						.open(version + 'pages')
						.then(function (cache) {
							cache.put(event.request, cacheCopy);
						});

					return response;
				}

				function unableToResolve() {
					return new Response('<h1>Service Unavailable</h1>', {
						status: 503,
						statusText: 'Service Unavailable',
						headers: new Headers({
							'Content-Type': 'text/html'
						})
					});
				}
			})
	);
});
