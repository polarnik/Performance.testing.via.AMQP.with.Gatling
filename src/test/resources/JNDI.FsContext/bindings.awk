#!/bin/awk -f bindings.awk .bindings

# cp .bindings .bindings.tmp && awk -f bindings.awk .bindings.tmp > .bindings && rm -f .bindings.tmp

BEGIN {
    # by default, when a for loop traverses an array, the order is undefined
    PROCINFO["sorted_in"] = "@ind_num_asc"
    FS = "="
}

{
    if ($0 ~ "#") {
        print $0
    } else {
        key = $1
        value = $2

        split(key, keyArray, "/")

        object = keyArray[1]
        part = keyArray[2]


        if (part == "RefAddr") {
            number = keyArray[3]
            attribute = keyArray[4]
            DATA[object]["RefAddr"][number][attribute] = value
        } else {
            specialPart = part
            DATA[object]["CUSTOM"][specialPart] = value
        }
    }
}

END {
    for (object in DATA) {
        for (specialPart in DATA[object]["CUSTOM"]) {
            value = DATA[object]["CUSTOM"][specialPart]

            print object "/" specialPart "=" value
        }
        print "#"

        for (number in DATA[object]["RefAddr"]) {
            for (attribute in DATA[object]["RefAddr"][number]) {
                value = DATA[object]["RefAddr"][number][attribute]

                print object "/" "RefAddr" "/" number "/" attribute "=" value
            }
            print "#"
        }
    }
}
