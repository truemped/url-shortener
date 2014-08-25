(ns url-shortener.core-test
  (:require [clojure.test :refer :all]
            [url-shortener.core :refer :all]))

(deftest simple-random-string-test
  (testing "Length is correct"
    (let [result (get-random-id 8)
          result1 (get-random-id 8)]
      (is (= (count result) 8))
      (is (not (= result result1))))))
