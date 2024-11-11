(ns mi-api-rest.routes
  (:require [reitit.ring :as ring]
            [mi-api-rest.usuarios :as usuarios]
            [ring.util.http-response :as response]
            [cheshire.core :as json]))

(defn obtener-todos-handler [_]
  (response/ok (usuarios/obtener-todos)))

(defn obtener-por-id-handler [{{:keys [id]} :path-params}]
  (let [usuario (usuarios/obtener-por-id (Integer/parseInt id))]
    (if usuario
      (response/ok usuario)
      (response/not-found {:error "Usuario no encontrado"}))))

(defn crear-usuario-handler [{:keys [body]}]
  (let [nuevo-usuario (json/parse-string (slurp body) true)]
    (response/created "/usuarios" (usuarios/crear-usuario nuevo-usuario))))

(defn actualizar-usuario-handler [{{:keys [id]} :path-params :as request}]
  (let [usuario-actualizado (json/parse-string (slurp (:body request)) true)]
    (if (usuarios/actualizar-usuario (Integer/parseInt id) usuario-actualizado)
      (response/ok (usuarios/obtener-por-id (Integer/parseInt id)))
      (response/not-found {:error "Usuario no encontrado"}))))

(defn eliminar-usuario-handler [{{:keys [id]} :path-params}]
  (if (usuarios/eliminar-usuario (Integer/parseInt id))
    (response/no-content)
    (response/not-found {:error "Usuario no encontrado"})))

(def app
  (ring/ring-handler
    (ring/router
      [["/usuarios"
        {:get obtener-todos-handler
         :post crear-usuario-handler}]
       ["/usuarios/:id"
        {:get obtener-por-id-handler
         :put actualizar-usuario-handler
         :delete eliminar-usuario-handler}]])))
