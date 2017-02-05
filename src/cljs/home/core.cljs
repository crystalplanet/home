(ns home.core
  (:require-macros [reagent.ratom :refer [reaction]])
  (:require [reagent.core :as reagent :refer [atom]]
            [re-frame.core :as rf]
            [goog.string :as gstring]
            [home.routes :as routes]))

(rf/reg-event-db
  :init-db
  (fn [_ _]
    {:show-navigation true
     :current-page :work
     :pages {:work "work"
             :about "about"
             :contact "contact"}
     :current-section nil
     :sections {:work [
                       ; :besidesprogramming
                       :brainly
                       :acejump
                       :identicons
                       :redshift]}
     :section-names {:besidesprogramming (str "besides"
                                              (gstring/unescapeEntities "&#8203;")
                                              "programming")
                     :brainly "brainly"
                     :acejump "acejump"
                     :identicons "identicons"
                     :redshift "redshift"}}))

(rf/reg-event-db
  :load-page
  (fn [db [_ page-name]]
    (let [is-home (= page-name "home")
          is-page (some #(= page-name %) (vals (:pages db)))]
      (if (or is-home is-page)
          (rf/dispatch [:open-page (keyword page-name)])
          (routes/navigate! "/" ""))
      db)))

(rf/reg-event-db
  :open-page
  (fn [db [_ page]]
    (.scrollTo js/window 0 0)
    (-> db
        (assoc :show-navigation (= page :home))
        (assoc :current-page (if (= page :home) (:current-page db) page))
        (assoc :current-section nil))))

(rf/reg-event-db
  :toggle-section
  (fn [db [_ section]]
    (-> db (assoc :current-section (when-not (= section (:current-section db)) section)))))

(rf/reg-sub-raw
  :show-navigation
  (fn [db _]
    (reaction (:show-navigation @db))))

(rf/reg-sub-raw
  :current-page
  (fn [db _]
    (let [page (reaction (:current-page @db))]
      (reaction {:key @page
                 :name (@page (:pages @db))
                 :sections (map (fn [s] [s (s (:section-names @db))])
                                (or (@page (:sections @db))))}))))

(rf/reg-sub-raw
  :current-section
  (fn [db _]
    (reaction (:current-section @db))))

(rf/dispatch-sync [:init-db])
