# \#crystalplanet

This is the code behind [crystalplanet.io](https://crystalplanet.io).  
If you're interested in the technical side of things and some of the decisions I made, read on. 

## The stack

[crystalplanet.io](https://crystalplanet.io) is a front-end application written entirely in ClojureScript - my programming language of choice.  
Some of the libraries I used include:

- [reagent](https://github.com/reagent-project/reagent): ClojureScript interface for React.
- [re-frame](https://github.com/Day8/re-frame): Library for reactive programming in ClojureScript.
- [secretary](https://github.com/gf3/secretary): Routing library.
- [markdown-clj](https://github.com/yogthos/markdown-clj): Converts Markdown to HTML and vice-versa.

I also use [Codeship](https://codeship.com) for Continuous Delivery.

## Technical facts

### Markdown

The site content is small and doesn't often change, so it makes sense to embed the content into the application. But even usual HTML templates can look ugly and become unreadable after adding a substantial amount of text. It gets even worse with reagent. Obviously that's not the way to go.  
On the other side, we have the lovely format called Markdown. It was created with the exact intention to provide a lightweight formatting syntax for plain text so that it can easily be converted to HTML. Is there any way I could use that? Of course there is.

First things first, I need a library that will convert markdown to html for me. Luckily, there's just that - [markdown-clj](https://github.com/yogthos/markdown-clj).

Because there's not a lot of content, and the nature of transition animations on the site, it's best to load the content at compile time rather than download it separately at runtime to keep things nice and snappy. This is done by the following macro:

```clj
(defmacro <-md
  [file]
  (markdown.core/md-to-html-string (slurp file)))
```

Clojure macros are expanded at compile time, replacing every instance with HTML markup from corresponding Markdown file.

### Continuous Delivery

Long gone are the days of manual deployments considering how much technology has to offer nowadays. As soon as I got the site up and running, I went looking for a simple solution to automate the process.

[Codeship](https://codeship.com) ended up as my platform of choice and I can definitely recommend it for dealing with small/personal projects. It's very easy to use and doesn't take up your attention once set up. Just like it should.

Since there's hardly any logic involved on the site, I don't have any tests and my pipeline is limited to building and uploading the code.  
The build script runs on top of their preconfigured JVM environment and looks like this:

```shell
# Install Boot, a build tool for Clojure - http://boot-clj.com/
wget -O "${HOME}/bin/boot" "https://github.com/boot-clj/boot-bin/releases/download/latest/boot.sh"
chmod u+x "${HOME}/bin/boot"
boot -u

# Build the app
boot production build target
```

As you can see, there's really not much to it. Assuming the build goes well, the contents of the ```target``` directory are then uploaded to the server.  
All of this takes place every time something is pushed to the `dev` branch.

