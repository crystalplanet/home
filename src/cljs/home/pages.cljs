(ns home.pages
  (:require [re-frame.core :as rf]
            [home.components :refer [back-button navigation social]]
            [home.sections :as sections]))

(defn about []
  [:div
    [:p "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Nulla faucibus nec purus sed pulvinar. Mauris at sapien egestas, cursus sem et, aliquam lorem. Proin vitae suscipit augue, eget ultricies risus. Aenean placerat posuere sapien a porttitor. Donec faucibus ex dui, et posuere tortor sollicitudin ac. Pellentesque dictum est at lacus fringilla, non pretium nunc efficitur. Donec tortor orci, fringilla sed risus at, egestas ultricies dui."]
    [:p "Sed ac feugiat metus. Mauris ultricies ultricies quam quis laoreet. Aenean pretium, massa sit amet sodales pellentesque, magna purus finibus quam, ac pharetra mi nibh sed nisi. Maecenas porttitor turpis a placerat placerat. Mauris id erat urna. Pellentesque congue euismod erat, sed gravida leo pharetra quis. Morbi sit amet tincidunt urna. Maecenas porttitor sapien lobortis libero pharetra, at tempor nunc laoreet. Curabitur aliquam consectetur eros, in mollis elit lacinia id. Quisque tempus risus sed tellus bibendum, sit amet volutpat ligula tincidunt."]])

(defn contact []
  [:div
    [:p "Duis arcu urna, convallis porta semper sed, scelerisque in nibh. Integer pulvinar nunc in vehicula laoreet. Praesent mattis quis quam eu eleifend. Aliquam luctus non sapien sit amet auctor. Vestibulum nisi lectus, consectetur congue vulputate eget, viverra rhoncus tortor. Etiam quis cursus dui. Nam nec luctus mauris. Maecenas ac neque non enim pharetra malesuada a at ligula. Proin est nibh, pulvinar nec ullamcorper cursus, faucibus id dui. Donec eu eros magna. Integer sagittis erat neque, quis pellentesque dui porttitor vel. Aenean luctus arcu tempor justo volutpat commodo. Aliquam a mi augue. In dapibus pellentesque nibh, sit amet venenatis mauris convallis sed. Nam laoreet nisl ut iaculis interdum."]
    [:p "Sed suscipit ligula eu est dapibus, vitae aliquet magna aliquam. Cras et nisl eget felis faucibus facilisis vel ut nibh. Nam condimentum nisl sit amet sem suscipit ultricies. In vitae sem eget enim facilisis varius et vel justo. Lorem ipsum dolor sit amet, consectetur adipiscing elit. Sed efficitur justo erat, quis tristique ligula hendrerit sit amet. Fusce vehicula elit vitae lacinia dignissim. In ipsum tortor, dapibus sollicitudin mauris nec, pretium pharetra risus. Vivamus fringilla non turpis malesuada commodo."]])

(defmulti pages identity)
(defmethod pages :about [] [about])
(defmethod pages :contact [] [contact])
(defmethod pages :default [] [:div])

(defn page-header
  ([] [:h2.page-header.g400
        [:a.page-header__link
          {:href "/"}
          [:span.g700 "crystalplanet"]]])

  ([page] [:h2.page-header.g400
            [:a.page-header__link
              {:href "/"}
              [:span.g700 "crystalplanet"]]
            [:span.page-header--end page]]))

(defn home-page [visible]
  [:div.page
    {:class (when (not visible) "page--left")}
    [page-header]
    [navigation]
    [social]])

(defn page [visible]
  (let [current-page (rf/subscribe [:current-page])]
    (fn [visible]
      [:div.page
        {:class (when (not visible) "page--back")}
        [page-header (:name @current-page)]
        [pages (:key @current-page)]
        (map #(with-meta [sections/section %] {:key (peek %)}) (:sections @current-page))
        [back-button]])))
