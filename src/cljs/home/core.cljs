(ns home.core
  (:require-macros [reagent.ratom :refer [reaction]])
  (:require [reagent.core :as reagent :refer [atom]]
            [re-frame.core :as rf]
            [goog.string :as gstring]
            [home.routes :as routes]))

(rf/register-handler
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

(rf/register-handler
  :load-page
  (fn [db [_ page-name]]
    (let [is-home (= page-name "home")
          is-page (some #(= page-name %) (vals (:pages db)))]
      (if (or is-home is-page)
          (rf/dispatch [:open-page (keyword page-name)])
          (routes/navigate! "/" ""))
      db)))

(rf/register-handler
  :open-page
  (fn [db [_ page]]
    (.scrollTo js/window 0 0)
    (-> db
        (assoc :show-navigation (= page :home))
        (assoc :current-page page)
        (assoc :current-section nil))))

(rf/register-handler
  :toggle-section
  (fn [db [_ section]]
    (-> db (assoc :current-section (when-not (= section (:current-section db)) section)))))

(rf/register-sub
  :show-navigation
  (fn [db _]
    (reaction (:show-navigation @db))))

(rf/register-sub
  :current-page
  (fn [db _]
    (let [page (reaction (:current-page @db))]
      (reaction {:key @page
                 :name (@page (:pages @db))
                 :sections (map (fn [s] [s (s (:section-names @db))])
                                (or (@page (:sections @db))))}))))

(rf/register-sub
  :current-section
  (fn [db _]
    (reaction (:current-section @db))))

(rf/dispatch-sync [:init-db])
