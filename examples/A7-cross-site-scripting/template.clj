(def logged-user-template "<html>
                          <head> <title>Welcome</title> </head>
                          <body> <h1>Welcome {{USERNAME}}</h1> </body>
                          </html>")

(defn render-template [username]
  (clojure.string/replace logged-user-template #"\{\{USERNAME\}\}" username))

(defn route-logged-user [{:keys [username]}]
  (render-template username))
