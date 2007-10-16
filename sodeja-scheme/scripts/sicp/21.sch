; cons/car/cdr
(def a (cons 2 3))
(car a)
(cdr a)

; lets impl
(let ((z 10)) (+ z z))

; vectors
(def (make-vector x y) (cons x y))
(def (xcor p) (car p))
(def (ycor p) (cdr p))

; line segments
(def (make-seg p q) (cons p q))
(def (seg-start s) (car s))
(def (seg-end s) (cdr s))

(def (midpoint s)
  (let ((a (seg-start s))
        (b (seg-end s)))
    (make-vector
      (average (xcor a) (xcor b))
      (average (ycor a) (ycor b)))))

(def (length s)
  (let
    ((dx (- (xcor (seg-end s))
            (xcor (seg-start s))))
     (dy (- (ycor (seg-end s))
            (ycor (seg-start s)))))
    (sqrt (+ (square dx)
             (square dy)))))

; cons air
(def (cons-a a b)
  (\ (pick)
    (cond ((= pick 1) a)
          ((= pick 2) b))))

(def (car-a x) (x 1))
(def (cdr-a x) (x 2))

(def ca (cons-a 5 7))
(car-a ca)
(cdr-a ca)
