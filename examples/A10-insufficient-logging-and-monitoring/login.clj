(def db {:matheus.bernardes "banana"})

(defn login [{:keys [username,password]}]
  (if (= ((key username) db) password)
    true
    false))

(login {:username "matheus.bernardes" :password "banana1"}
