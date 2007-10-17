; simple staff
3
(+ 3 4 8)
(+ 3 (* 5 6) 8 2)
(+ (* 3 5) (* 47 (- 20 6)) 12)

; value definition
(def a (* 5 5))
(* a a)
(def b (+ a (* 5 a)))
b
(+ a (/ b 5))

; function definition
(def squareLambda (\ (x) (* x x)))
(def (square x) (* x x))
(square 10)
(square 1001)
(square (+ 5 7))
(+ (square 3) (square 4))
(square (square (square 1001)))

; some other functions
(def (abs x) (if (< x 0) (- x) x))
(def (average x y) (/ (+ x y) 2))
(def (mean-square x y) (average (square x) (square y)))

; sqrt plain
(def (improve guess x) (average guess (/ x guess)))
(def (good-enough? guess x) (< (abs (- (square guess) x)) 0.001))
(def (try guess x) (if (good-enough? guess x) guess (try (improve guess x) x)))
(def (sqrt1 x) (try 1 x))
(sqrt1 2)

; sqrt boxed
(def (sqrt x)
  (def (good-enough? guess) (< (abs (- (square guess) x)) 0.001))
  (def (improve guess) (average guess (/ x guess)))
  (def (try guess) (if (good-enough? guess) guess (try (improve guess))))
  (try 1.0))
(sqrt 2)
