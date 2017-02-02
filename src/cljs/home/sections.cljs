(ns home.sections
  (:require [re-frame.core :as rf]
            [home.components :refer [css-transition-group]]))

; (defn besidesprogramming []
;   [:p.content "To Do"])

(defn brainly []
  [:div
   [:p.content__text
    "Brainly is the world's largest social learning network. During my time at the company, Brainly consisted of 19 websites in a variety of languages, hitting 40M monthly unique users."]
   [:p.content__text
    "As a QA Engineer, my job was to maintain our selenium test suite. I managed to completely rewrite the existing codebase and quadruple the performance. While being part of Brainly's core team, I also worked with our technical teams to introduce continuous integration & delivery, TDD and BDD."]])

(defn acejump []
  [:div
   [:p.content__text
    "AceJump is a Sublime Text 3 plugin, enabling easy movement between characters and lines in the open window. It is based on "
    [:a
     {:href "https://www.emacswiki.org/emacs/AceJump" :target "_blank"}
     "an emacs mode with the same name"]
    ". AceJump for emacs proved crazy helpful for me and I decided to bring some of its goodness back to Sublime Text."]
   [:p.content__text
    "The plugin is available on "
    [:a {:href "https://packagecontrol.io/packages/AceJump" :target "_blank"} "Package Control"]
    " and has accumulated over 5000 downloads so far."
    [:br]
    "The source code is on "
    [:a {:href "https://github.com/ice9js/ace-jump-sublime" :target "_blank"} "GitHub"]
    "."]
   ; [:img.content__img {:src "/img/acejump.png"}]
   ])

(defn identicons []
  [:div
   [:p.content__text
    "Identicon is a PHP library for generating identicons, designed with extendability in mind. It currently supports GitHub-style as well as circular icons, and outputs them as SVG files."]
   [:p.content__text
    "I needed to generate circular identicons, but unfortunately all existing libraries only seemed to support the square and symmetric GitHub variant. So I decided to write my own library, where adding a new style would just require writing a simple generator function."]
   [:p.content__text
    "The code is on "
    [:a {:href "https://github.com/bitverseio/identicon" :target "_blank"} "GitHub"]
    " and the library is also available on "
    [:a {:href "https://packagist.org/packages/bitverse/identicon" :target "_blank"} "Packagist"]
    "."
    [:br]
    "There also is a "
    [:a {:href "https://github.com/bitverseio/BitverseIdenticonBundle" :target "_blank"} "Symfony bundle"]
    " for the package."]])

(defn redshift []
  [:div
   [:p.content__text
    "Redshift is an experimental library for async programming and communication PHP7."]
   [:p.content__text
    "I first found out about "
    [:a {:href "https://en.wikipedia.org/wiki/Communicating_sequential_processes" :target "_blank"} "CSP"]
    " when working with ClojureScript. It struck me as a very elegant solution for a quite complex problem. Redshift is my attempt at implementing that behavior in PHP, which is a strictly synchronous language by itself."]
   [:p.content__text
    "The library consists of a very basic event loop to execute asynchronous tasks, as well as I/O streams and channels to allow communication between them."]
   [:p.content__text
    "The code paired with some examples can be found on "
    [:a {:href "https://github.com/crystalplanet/redshift" :target "_blank"} "GitHub"]
    "."]])

(defmulti sections identity)
; (defmethod sections :besidesprogramming [] [besidesprogramming])
(defmethod sections :brainly [] [brainly])
(defmethod sections :acejump [] [acejump])
(defmethod sections :identicons [] [identicons])
(defmethod sections :redshift [] [redshift])
(defmethod sections :default [] [:div])

(defn section-header [section name]
  [:button.section-header.g700
   {:on-click #(rf/dispatch [:toggle-section section])}
   name])

(defn section-content [section]
  [:div.section-content
   (sections section)])

(defn section [section]
  (let [current-section (rf/subscribe [:current-section])]
    (fn [[section name]]
      [:div
       [section-header section name]
       [css-transition-group
        {:transition-name (str "section--" name)
         :transition-leave-timeout 501
         :transition-enter-timeout 1}
        (when (= @current-section section)
          [section-content section])]])))
