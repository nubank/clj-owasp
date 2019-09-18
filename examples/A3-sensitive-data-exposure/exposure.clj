(defn create-user [{:keys [username]} {:keys [token]}]
  (try
    (aws/create-iam-user token username)
    (catch Exception _
      (throw ("Error to create user" username "using token " token)))))

(create-user {:username "matheus.bernardes"} {:token "AJWJASKDALSDJLKASJD"})
