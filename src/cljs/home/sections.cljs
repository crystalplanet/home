(ns home.sections
  (:require [re-frame.core :as rf]
            [home.components :refer [css-transition-group]]))

(defn besidesprogramming []
  [:p "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Nulla faucibus nec purus sed pulvinar. Mauris at sapien egestas, cursus sem et, aliquam lorem. Proin vitae suscipit augue, eget ultricies risus. Aenean placerat posuere sapien a porttitor. Donec faucibus ex dui, et posuere tortor sollicitudin ac. Pellentesque dictum est at lacus fringilla, non pretium nunc efficitur. Donec tortor orci, fringilla sed risus at, egestas ultricies dui."])

(defn brainly []
  [:p "Sed ac feugiat metus. Mauris ultricies ultricies quam quis laoreet. Aenean pretium, massa sit amet sodales pellentesque, magna purus finibus quam, ac pharetra mi nibh sed nisi. Maecenas porttitor turpis a placerat placerat. Mauris id erat urna. Pellentesque congue euismod erat, sed gravida leo pharetra quis. Morbi sit amet tincidunt urna. Maecenas porttitor sapien lobortis libero pharetra, at tempor nunc laoreet. Curabitur aliquam consectetur eros, in mollis elit lacinia id. Quisque tempus risus sed tellus bibendum, sit amet volutpat ligula tincidunt."])

(defn acejump []
  [:p "Duis arcu urna, convallis porta semper sed, scelerisque in nibh. Integer pulvinar nunc in vehicula laoreet. Praesent mattis quis quam eu eleifend. Aliquam luctus non sapien sit amet auctor. Vestibulum nisi lectus, consectetur congue vulputate eget, viverra rhoncus tortor. Etiam quis cursus dui. Nam nec luctus mauris. Maecenas ac neque non enim pharetra malesuada a at ligula. Proin est nibh, pulvinar nec ullamcorper cursus, faucibus id dui. Donec eu eros magna. Integer sagittis erat neque, quis pellentesque dui porttitor vel. Aenean luctus arcu tempor justo volutpat commodo. Aliquam a mi augue. In dapibus pellentesque nibh, sit amet venenatis mauris convallis sed. Nam laoreet nisl ut iaculis interdum."])

(defn identicons []
  [:p "Sed suscipit ligula eu est dapibus, vitae aliquet magna aliquam. Cras et nisl eget felis faucibus facilisis vel ut nibh. Nam condimentum nisl sit amet sem suscipit ultricies. In vitae sem eget enim facilisis varius et vel justo. Lorem ipsum dolor sit amet, consectetur adipiscing elit. Sed efficitur justo erat, quis tristique ligula hendrerit sit amet. Fusce vehicula elit vitae lacinia dignissim. In ipsum tortor, dapibus sollicitudin mauris nec, pretium pharetra risus. Vivamus fringilla non turpis malesuada commodo."])

(defn redshift []
  [:p "Sed placerat at turpis vitae lacinia. Class aptent taciti sociosqu ad litora torquent per conubia nostra, per inceptos himenaeos. Curabitur ut rhoncus nibh, sed fermentum orci. Suspendisse pellentesque eleifend velit at aliquet. Mauris facilisis est et mollis pellentesque. Praesent non felis sit amet dui scelerisque fringilla in nec tortor. Morbi eu metus ligula. Etiam porttitor urna fermentum purus consectetur condimentum. Phasellus mollis sit amet ipsum et molestie. Nullam semper condimentum venenatis. Mauris suscipit ante quam. Donec fringilla scelerisque odio vitae euismod. Pellentesque habitant morbi tristique senectus et netus et malesuada fames ac turpis egestas."])

(defmulti sections identity)
(defmethod sections :besidesprogramming [] [besidesprogramming])
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
          {:transition-name "section-content"
           :transition-leave-timeout 501
           :transition-enter-timeout 1}
          (when (= @current-section section)
            [section-content section])]])))
