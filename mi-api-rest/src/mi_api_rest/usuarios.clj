(ns mi-api-rest.usuarios
  (:require [next.jdbc :as jdbc]
            [next.jdbc.sql :as sql]))

(defn get-datasource []
  (jdbc/get-datasource {:jdbc-url (System/getenv "DATABASE_URL")
                        :username "usuario"
                        :password "contraseña"}))

(def ds (get-datasource))

(defn obtener-todos []
  (sql/query ds ["SELECT * FROM usuarios"]))

(defn obtener-por-id [id]
  (sql/get-by-id ds :usuarios id))

(defn crear-usuario [usuario]
  (sql/insert! ds :usuarios usuario))

(defn actualizar-usuario [id usuario]
  (let [result (sql/update! ds :usuarios usuario {:id id})]
    (not (empty? result))))

(defn eliminar-usuario [id]
  (let [result (sql/delete! ds :usuarios {:id id})]
    (not (empty? result))))
