(defproject url-shortener "0.1.0-SNAPSHOT"

  :description "Simple URL shortener in contrast to this one: http://grasswire-engineering.tumblr.com/post/94043813041/a-url-shortener-service-in-45-lines-of-scala"
  :url "http://example.com/FIXME"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}

;  :main news-tweets.core

  :plugins [
            [lein-ring "0.8.10"]
            ]
  :ring {:handler url-shortener.core/app}

  :dependencies [[org.clojure/clojure "1.6.0"]
                 [org.clojure/data.json "0.2.5"]

                 ; rest stuff
                 [compojure "1.1.6"]
                 [ring/ring-core "1.2.1"]
                 [ring/ring-headers "0.1.0"]
                 [ring/ring-json "0.2.0"]

                 ; core.async
                 [org.clojure/core.async "0.1.303.0-886421-alpha"]
                 ; redis
                 [com.taoensso/carmine "2.6.2"]]

  :profiles {
             :dev {
                   :dependencies [[javax.servlet/servlet-api "2.5"]
                                  [ring-mock "0.1.5"]]}})
