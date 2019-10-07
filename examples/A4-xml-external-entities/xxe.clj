(ns playground
  (:require [clojure.xml :as xml]))

(defn parse-document [xml-document] 
  (xml/parse xml-document))

(defn get-document [token]
  (->
    (client/get "https://serasa.com.br/api/v1/clients/info")
    (parse-document))
