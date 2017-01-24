(ns home.app
  (:require [reagent.core :as reagent]
            [re-frame.core :as rf]
            [home.routes]
            [home.core]
            [home.pages :as pages]))

(defn app []
  (let [show-navigation (rf/subscribe [:show-navigation])]
    (fn []
      [:div.pages
        [pages/home-page @show-navigation]
        [pages/page (not @show-navigation)]])))

(defn init []
  (reagent/render-component [app]
                            (.getElementById js/document "app")))
