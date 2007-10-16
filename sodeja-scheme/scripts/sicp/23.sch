; SELECTORS
; (real z)
; (imag z)
; (mag z)
; (angle z)
; CONSTRUCTORS
; (make-rect x y)
; (make-polar r a)

(def (+c z1 z2)
  (make-rect
    (+ (real z1) (real z2))
    (+ (imag z1) (imag z2))))

(def (-c z1 z2)
  (make-rect
    (- (real z1) (real z2))
    (- (imag z1) (imag z2))))

(def (*c z1 z2)
  (make-rect
    (* (mag z1) (mag z2))
    (+ (angle z1) (angle z2))))

(def (/c z1 z2)
  (make-rect
    (/ (mag z1) (mag z2))
    (- (angle z1) (angle z2))))

; Rect
(def (make-rect x y) (cons x y))

(def (real z) (car z))

(def (imag z) (cdr z))

(def (make-polar r a)
  (cons (* r (cos a)) (* r (sin a))))

(def (mag z)
  (sqrt (+ (square (car z))
           (square (cdr z)))))

(def (angle z)
  (atan (cdr z) (car z)))

(def z1 (make-rect 1 1))

(def z2 (make-rect 2 2))

(+c z1 z2)

(*c z1 z2)
