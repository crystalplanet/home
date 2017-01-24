(ns home.routes
    (:require-macros [secretary.core :refer [defroute]])
    (:import goog.history.Html5History
             goog.Uri)
    (:require [secretary.core :as secretary]
              [goog.events :as events]
              [goog.history.EventType :as EventType]
              [re-frame.core :as rf]))

(defn get-href [element]
  (if (= "app" (.-id element))
    ""
    (or (.-href element) (get-href (.-parentNode element)))))

(defn get-uri [link]
  (.getPath (.parse Uri link)))

(defn hook-browser-navigation! []
  (let [history (doto (Html5History.)
                  (events/listen
                    EventType/NAVIGATE
                    (fn [event]
                      (secretary/dispatch! (.-token event))))
                  (.setUseFragment false)
                  (.setPathPrefix "")
                  (.setEnabled true))]
    (events/listen js/document "click"
      (fn [e]
        (let [link (get-href (.-target e))
              path (get-uri link)
              title (.-title (.-target e))]
          (when (and
                (re-matches #"^https?://(.+\.)?(localhost:?|crystalplanet\.).*" (str "" link))
                (secretary/locate-route path))
            (. e stopPropagation)
            (. e preventDefault)
            (. history (setToken path title))))))))

(defn navigate! [path title]
  (let [history (doto (Html5History.)
                  (events/listen
                    EventType/NAVIGATE
                    (fn [event]
                      (secretary/dispatch! (.-token event))))
                  (.setUseFragment false)
                  (.setPathPrefix "")
                  (.setEnabled true))]
    (. history (setToken path title))))

(defn app-routes []
  (defroute "/" [] (rf/dispatch [:load-page "home"]))
  (defroute "/:page" [page] (rf/dispatch [:load-page page]))

  (hook-browser-navigation!))

(app-routes)
