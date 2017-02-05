Identicon is a **PHP library for generating identicons**, designed with extendability in mind. It currently supports GitHub-style as well as circular icons, and outputs them as SVG files.

I needed to generate circular identicons, but unfortunately all existing libraries only seemed to support the square and symmetric GitHub variant. So I decided to write my own library, where adding a new style would just require writing a simple generator function.

The code is on [GitHub](https://github.com/bitverseio/identicon) and the library is available on [Packagist](https://packagist.org/packages/bitverse/identicon).  
There also is a [Symfony bundle](https://github.com/bitverseio/BitverseIdenticonBundle) for the package.
