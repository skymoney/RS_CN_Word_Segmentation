#-*-coding:utf-8-*-

############################################
#    Name:main.py
#    Description: implementation of Chinese words
#
#    Author:Qian Cheng
#    Department:iSE Research Lab,Software Institute,Nanjing University
#    Mail: CChain0615@gmail.com
############################################

import re   
def PreProcess():   
    #build dictionary via words lib
    file=open("SogouLabDic2.dic")
    dict={}
    for line in file:
        word=line.split(" ")[0]
        dict[word]=1
    return dict
  
def FMM(sentence,diction,result = [],maxwordLength = 4,edcode="utf-8"):   
    i = 0   
    length = len(sentence)   
    while i < length:  
        # find the ascii word   
        tempi=i   
        tok=sentence[i:i+1]   
        while re.search("[0-9A-Za-z\-\+#@_\.]{1}",tok)<>None:   
            i= i+1   
            tok=sentence[i:i+1]   
        if i-tempi>0:   
            result.append(sentence[tempi:i].lower())  
        # find chinese word   
        left = len(sentence[i:])   
        if left == 1:   
            """go to 4 step over the FMM"""  
            """should we add the last one? Yes, if not blank"""  
            if sentence[i:] <> " ":   
                result.append(sentence[i:])   
            return result   
        m = min(left,maxwordLength)   
           
        for j in xrange(m,0,-1):   
            leftword = sentence[i:j+i]  
         #   print leftword.decode(edcode)   
            if LookUp(leftword,diction):  
                # find the left word in dictionary  
                # it's the right one   
                i = j+i   
                result.append(leftword)   
                break  
            elif j == 1:   
                """only one word, add into result, if not blank"""  
                if leftword <> " ":   
                    result.append(leftword)   
                i = i+1   
            else:   
                continue  
    return result   
def LookUp(word,dictionary):   
    if dictionary.has_key(word):   
        return True   
    return False   


if __name__=="__main__":
    print "初始化训练集词库...."
    diction=PreProcess()
    print "训练完成..."
    print diction.popitem()
    sentence="这是中文分词的测试用例"
    s=FMM(sentence,diction)
    for i in s:
        print i