;(def a (* 3 4))
;a
;(set! a (* 5 5))
;a
;(def b 's)
;b
;(def b '(a b))
;
;(def count 1)
;(def (demo x)
;  (set! count (+ 1 count))
;  (+ x count))
;  
;(demo 3)
;
;(demo 3)
;
;(def (fact n)
;  (let ((i 1) (m 1))
;    (def (loop)
;      (cond ((> i n) m)
;            (else
;              (set! m (* i m))
;              (set! i (+ i 1))
;              (loop))))
;    (loop)))
;
;(fact 6)
;

(def (make-counter n) (\ () (set! n (+ 1 n)) n))

(def c1 (make-counter 0))

(def c2 (make-counter 10))

(c1)
(c2)
(c1)
(c2)
