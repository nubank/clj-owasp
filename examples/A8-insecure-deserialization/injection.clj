(defn get-token [bucket-path]
  (->
    (aws/s3 (str bucket-path "/secret.edn"))
    (read-string))

(def components [components] 
  (let [token (get-token "nu-secrets-br-prod/slack")]
    (merge components token)))

(def main [{:keys [message username]}] 
  (slack/post-message components username memessage))
