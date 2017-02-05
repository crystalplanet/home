Redshift is an experimental **library for async programming and communication PHP7**. It's based on goroutines and core.async.

I found out about [CSP](https://en.wikipedia.org/wiki/Communicating_sequential_processes) when working with ClojureScript. It struck me as a very elegant solution for a quite complex problem. Redshift is my attempt at implementing that behavior in PHP, which is a strictly synchronous language by itself.

The library consists of a very basic **event loop** to execute asynchronous tasks, as well as **I/O streams** and **message channels** to allow for communication.

The code paired with some examples can be found on [GitHub](https://github.com/crystalplanet/redshift).
