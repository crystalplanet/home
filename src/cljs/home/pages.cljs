(ns home.pages
  (:require-macros [home.macros :refer [<-md]])
  (:require [re-frame.core :as rf]
            [home.components :refer [back-button navigation social]]
            [home.sections :as sections]))

(defmulti pages identity)
(defmethod pages :about [] (<-md "content/about.md"))
(defmethod pages :contact [] (<-md "content/contact.md"))
(defmethod pages :default [] "")

(defn page-header
  ([] (page-header nil))
  ([page] [:h2.page-header.g400
            [:a.page-header__main
              {:href "/"}
              [:span.g700 "crystalplanet"]]
            (when page [:span.page-header__sub page])]))

(defn home-page []
  [:div.page.page--slide
    [page-header]
    [navigation]
    [social]])

(defn page []
  (let [current-page (rf/subscribe [:current-page])]
    (fn []
      [:div.page.page--pop
        [page-header (:name @current-page)]
        [:div.content {:dangerouslySetInnerHTML {:__html (pages (:key @current-page))}}]
        (map #(with-meta [sections/section %] {:key (peek %)}) (:sections @current-page))
        [back-button]])))
