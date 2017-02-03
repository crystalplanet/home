(ns home.sections
  (:require-macros [home.macros :refer [<-md]])
  (:require [re-frame.core :as rf]
            [home.components :refer [css-transition-group]]))

(defmulti sections identity)
; (defmethod sections :besidesprogramming [] (<-md "content/besidesprogramming.md"))
(defmethod sections :brainly [] (<-md "content/brainly.md"))
(defmethod sections :acejump [] (<-md "content/acejump.md"))
(defmethod sections :identicons [] (<-md "content/identicons.md"))
(defmethod sections :redshift [] (<-md "content/redshift.md"))
(defmethod sections :default [] [:div])

(defn section-header [section name]
  [:button.section-header.g700
   {:on-click #(rf/dispatch [:toggle-section section])}
   name])

(defn section-content [section]
  [:div.section-content.content
   {:dangerouslySetInnerHTML {:__html (sections section)}}])

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
