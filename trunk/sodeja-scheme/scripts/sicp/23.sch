; SELECTORS
; (real z)
; (imag z)
; (magn z)
; (angle z)
; CONSTRUCTORS
; (make-rect x y)
; (make-polar r a)
(def (+c z1 z2)
  (make-rect
    (+ (real z1) (real z2))
    (+ (imag z1) (imag z2))))
