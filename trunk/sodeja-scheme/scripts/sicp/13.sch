(def (sum term a next b)
  (if (> a b)
      0
      (+ (term a)
         (sum term (next a) next b))))

(def (inc x) (+ x 1))

(def (sum-int a b)
  (def (identity x) x)
  (sum identity a inc b))
  
(sum-int 3 5)

(def (sum-sq a b)
  (def (square x) (* x x))
  (sum square a inc b))
  
(sum-sq 3 5)

(def (pi-sum a b)
  (sum (\ (i) (/ 1 (* i (+ i 2))))
       a
       (\ (i) (+ i 4))
       b))

(* 8 (pi-sum 1 300))

;(def (average x y) (/ (+ x y) 2))

(def (sqrt1 x)
  (fixed-point (\ (y) (average (/ x y) y)) 1))
  
(def (abs x) (if (< x 0) (- x) x))

(def (fixed-point f start)
  (def tolerance 0.00001)
  (def (close-enuf? u v)
    (< (abs (- u v)) tolerance))
  (def (iter old new)
    (if (close-enuf? old new)
    new
    (iter new (f new))))
  (iter start (f start)))

(sqrt1 2)

(def (sqrt x)
  (fixed-point
    (average-damp (\ (y) (/ x y)))
    1))

(def (average-damp f)
  (\ (x) (average (f x) x)))

(sqrt 2)
