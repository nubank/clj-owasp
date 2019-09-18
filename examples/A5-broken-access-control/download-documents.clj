(defn download-document [document-id]
  (aws/s3 get (str "nu-attachments/documents/pii/" document-id)))

(defn route-download [{:keys [cookie document-id]}]
  (validate-user-authenticated cookie)
  (download-document document-id))
