(ns my-api-rest.usuarios
  (:require [next.jdbc :as jdbc]
            [next.jdbc.sql :as sql]))

(defn get-datasource []
  (jdbc/get-datasource {:dbtype "postgresql"
                        :host "db"
                        :port 5432
                        :dbname "mi_api_rest_db"
                        :user "usuario"
                        :password "password"}))

(defn obtener-todos []
  (sql/query (get-datasource) ["SELECT * FROM usuarios"]))

(defn obtener-por-id [id]
  (sql/get-by-id (get-datasource) :usuarios id))

(defn crear-usuario [usuario]
  (sql/insert! (get-datasource) :usuarios usuario))

(defn actualizar-usuario [id usuario]
  (let [result (sql/update! (get-datasource) :usuarios usuario {:id id})]
    (not (empty? result))))

(defn eliminar-usuario [id]
  (let [result (sql/delete! (get-datasource) :usuarios {:id id})]
    (not (empty? result))))
