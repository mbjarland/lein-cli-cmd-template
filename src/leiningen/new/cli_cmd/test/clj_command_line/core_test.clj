(ns {{ns-name}}.core-test
  (:require [midje.sweet :refer :all]
            [{{ns-name}}.core :refer :all]))

; midje tests are run by issuing 'lein midje'
(tabular
  (fact "I should fail in a tabular way"
        (= ?first ?second) => ?expected-result)
  ?first     ?second     ?expected-result
  "alice"    "raven"     true
  )

(fact "I should fail in a singular way"
      (= :truthy :falsey) => true)