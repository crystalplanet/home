(ns home.components
  (:require [reagent.core :as reagent]))

(def css-transition-group
  (reagent/adapt-react-class js/React.addons.CSSTransitionGroup))

(defn link [page]
  [:a.nav__link.g700
    {:href (:url page)}
    (:name page)])

(defn navigation []
  [:ul.nav
    [:li.nav__item [link {:url "/work" :name "work"}]]
    [:li.nav__item [link {:url "/about" :name "about"}]]
    [:li.nav__item [link {:url "/contact" :name "contact"}]]])

(defn social-link [icon-class url]
  [:a.social__link
    {:href url
     :rel "noopener"
     :target "_blank"}
    [:i.fa {:class icon-class}]])

(defn social []
  [:div.social
    [social-link "fa-twitter" "https://twitter.com/ice9js"]
    [social-link "fa-instagram" "https://instagram.com/ice9js/"]
    [social-link "fa-github" "https://github.com/ice9js"]
    [social-link "fa-linkedin" "https://linkedin.com/in/kbirecki"]
    [social-link "fa-stack-overflow" "https://stackoverflow.com/users/4782314/kuba-birecki"]])

(defn back-button []
  [:a.btn.btn--back {:href "/"} "back"])
