(def buckets {
              :nu-dev     :infosec
              :nu-keyset  :infosec
              :nu-ronaldo :platform})

(def users->squad {
                   :matheus.bernardes :infosec
                   :gabriel.diab      :infosec
                   :yago.nobre        :platform})

(defn check-permission-bucket [{:keys [squad bucket] :as input}]
  (if (= squad (bucket buckets))
    true))

(defn get-user-group [{:keys [username] :as input}]
  (assoc input :squad (username users->squad)))

(defn set-permission [{:keys [username bucket]}]
  (aws/put-policy bucket username))

(defn main [input]
  (let [builded-input (get-user-group input)]
    (check-permission-bucket builded-input)
    (set-permission builded-input)))

(main {:username :matheus.bernardes :bucket :nu-ronaldo})

