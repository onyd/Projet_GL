#!/usr/bin/env python3

import os
import re
import sys

def main():
    project_path = os.path.dirname(__file__) + "/../../.."
    test_path = project_path + "/src/test/deca/syntax/valid/custom/"
    for f in os.listdir(test_path):
        ret = os.system(project_path + "/src/test/script/launchers/test_synt " + test_path + f + " &> /dev/null")
        if ret != 0:
            print(f + " doesn't work")
        else:
            print(f + " OK")

if __name__ == "__main__":
    main()
