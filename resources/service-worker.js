const version = '2018:01';

const addToCache = (request, response) => {
	if (!response.ok) {
		return;
	}

	const copy = response.clone();
	caches.open(version).then(cache => cache.put(request, copy));

	return response;
};

self.addEventListener('install', event => event.waitUntil(
	caches.open(version)
		.then(cache => cache.addAll([
			'/',
			'/css/less.css',
			'/fonts/GalanoGrotesqueBold.otf',
			'/fonts/GalanoGrotesqueLight.otf',
			'/js/app.js',
		]))
));

self.addEventListener('fetch', event => {
	if (event.request.method !== 'GET') {
		return;
	}

	event.respondWith(
		caches.match(event.request)
			.then(cachedResponse => {
				const networkResponse = fetch(event.request)
					.then(response => addToCache(event.request, response));

				return cachedResponse || networkResponse;
			})
			.catch(() => new Response('<h1>Service Unavailable</h1>', {
				status: 503,
				statusText: 'Service Unavailable',
				headers: { 'Content-Type': 'text/html' }
			}))
		);
});

self.addEventListener('activate', event => event.waitUntil(
	caches.keys()
		.then(keys => Promise.all(
			keys
				.filter(key => !key.startsWith(version))
				.map(key => caches.delete(key))
		))
));
