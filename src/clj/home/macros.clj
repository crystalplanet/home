(ns home.macros
  (:use markdown.core))

(defmacro <-md
  "Loads a markdown file and transforms it to a html string at compile time."
  [file]
  (md-to-html-string (slurp file)))
