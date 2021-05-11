# AnalysisOfAlgorithms-LEDS_Dynamic

Given two circuit boards (S and L), where S contains n power sources, while L contains n
LEDs. The sources on S are sorted in ascending order <1, 2, 3, … , n>, while the LEDs on L are not <2, 9, 5, 14, 3,
…>. We have to connect each LED in L to its pair in S (i.e., 1 with 1, 2 with 2) through unshielded wires,
thus when a wire connects a LED (li) in L with its corresponding source in S (si), it may prevent other LEDs from
being connected (no two wires may cross). This has been done by using the Dynamic Programming strategy to
find the optimal solution (Max LED Lighting).
