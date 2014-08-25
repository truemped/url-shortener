(ns url-shortener.core
  (:require
    [ring.util.response :refer [response redirect status]]
    [ring.middleware.json :as json-mw]
    [compojure.core :refer :all]
    [compojure.handler :as handler]
    [compojure.route :as route]
    [compojure.core :refer [POST GET defroutes]]
    [taoensso.carmine :as car :refer [wcar]]))

(def alphabet "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz")
(defn get-random-id [length]
  (apply str (take length (repeatedly #(rand-nth alphabet)))))

(def redis-connection
  {:pool {:max-active 8}
   :spec {:host "localhost"
          :port 6379
          :timeout 4000}})

(defmacro wcar* [& body]
  `(car/wcar redis-connection ~@body))

(defn create-short-url [url]
  (let [new-id (get-random-id 10)]
    (wcar* (car/hset "short-urls" new-id url))
    (response {:short-id new-id
               :url url
               :link (str "http://localhost:3000/" new-id)})))

(defn short-url-redirect [short-id]
  (let [url (wcar* (car/hget "short-urls" short-id))]
    (if url
      (status (redirect url) 301)
      (route/not-found "Unknown id"))))

(defroutes url-shortener
  (POST "/" {body :body}
        (create-short-url (slurp body)))
  (GET "/:short-id" [short-id]
       (short-url-redirect short-id)))

(def app
  (-> (routes url-shortener)
      (json-mw/wrap-json-response)))
