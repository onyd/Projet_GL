#!/usr/bin/env python3

import os
import re
import sys

def main():
    project_path = os.path.dirname(__file__) + "/../../.."
    for f in os.listdir(project_path + "/src/test/java/fr/ensimag/deca/syntax/"):
        f = re.sub(".java", "", f)
        if f != "ManualTestLex" and f != "AutomaticTestLex" and "Lex" in f:
            ret = os.system(project_path + "/src/test/script/launchers/test_lex_file " + f + " &> /dev/null")
            if ret != 0:
                print(f + " doesn't work")
            else:
                print(f + " OK")

if __name__ == "__main__":
    main()
