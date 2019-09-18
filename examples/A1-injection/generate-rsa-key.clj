(def keys-path "/tmp/keys/")

(defn generate-rsa-key [{:keys [key-path]}]
  (println (sh "bash" "-c" (str "openssl genrsa -out" key-path) "2048")))

(defn build [{:keys [key-name] :as input}]
  (assoc input :key-path (str keys-path key-name)))

(defn read-rsa-key [{:keys [key-path]}]
  (slurp key-path))

(defn main [input]
  (let [input (build input)]
    (generate-rsa-key input)
    (read-rsa-key input)))

(main {:key-name "teste"})
(main {:key-name "teste;touch /tmp/keys/youhavebeenhacked #"})
