(ns home.app
  (:require [reagent.core :as reagent]
            [re-frame.core :as rf]
            [home.routes]
            [home.core]
            [home.components :refer [css-transition-group]]
            [home.pages :as pages]))

(defn app []
  (let [show-navigation (rf/subscribe [:show-navigation])]
    (fn []
      [:div.pages
        [css-transition-group
          {:transition-name "page--slide"
           :transition-leave-timeout 501
           :transition-enter-timeout 1}
          (when @show-navigation [pages/home-page])]
        [css-transition-group
          {:transition-name "page--pop"
           :transition-leave-timeout 501
           :transition-enter-timeout 1}
          (when-not @show-navigation [pages/page])]])))

(defn worker []
  (when (.-serviceWorker js/navigator)
        (-> (.-serviceWorker js/navigator)
            (.register "/service-worker.js")
            (.then #(.log js/console "Installed service worker.")))))

(defn init []
  (worker)
  (reagent/render-component [app]
                            (.getElementById js/document "app")))
