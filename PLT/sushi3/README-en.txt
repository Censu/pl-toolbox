### Usage License #################################################

- We involve Toshihiro Kamishima, his colleagues, and their employers.
- You involve the user of this data and his/her colleagues, and their employers.
- We are NOT liable for any damages or losses, arising out of or related to your use or inability to use this data set.
- You can use this data set for any research purpose.
- You must not redistribute without our permission.
- You must acknowledge the use of the data set in publications resulting from the use of this data set by citing one of our related publications.


### file contents #################################################

README-ja.txt         README (Japanese)
README-en.txt         README (English)
README-stat-ja.txt    summary statics of this data set (Japanese)

sushi3.idata          features of items (=SUSHIs)
sushi3.udata          features of users

sushi3a.5000.10.order preference order of SUSHIs in the item set A
                      which is acquired in the step 1
sushi3b.5000.10.order preference order of SUSHIs in the item set B
                      which is acquired in the step 5
sushi3b.5000.10.score preference score of SUSHIs in the item set B
                      which is acquired in the step 2

These preference data sets are collected by the following questionnaire survey as described in our paper:
[1] T. Kamishima, "Nantonac Collaborative Filtering: Recommendation Based on Order Responses", KDD2003, pp.583-588 (2003)

STEP 1: (ranking method 1)
  We asked each respondent i to sort objects in the item set "A" according to his/her preference. The respondent selected ranks of presented objects.
STEP 2: scoring method
  We asked the respondent i to rate their preferences in objects of the item set "B" by the scoring method using a five-point-scale.
STEP 3: 
  How oily do you think these SUSHI are?
STEP 4:
  How frequently do you eat these SUSHI?
STEP 5: (ranking method 2)
  We asked the respondent i to sort the objects in the item set "B" according to his/her preference.
STEP 6:
  Some questions about demographic information of the respondent were given.
\end{quote}


### Item ID #######################################################

see also http://en.wikipedia.org/wiki/Sushi

*** ID for the item set A

0:ebi          1:anago        2:maguro       3:ika          4:uni
 (shrimp)        (sea eel)      (tuna)         (squid)        (sea urchin)
5:sake         6:tamago       7:toro         8:tekka-maki   9:kappa-maki
 (salmon roe)   (egg)          (fatty tuna)   (tuna roll)    (cucumber roll)

*** ID for the item set B

 0:ebi (shrimp)
 1:anago (sea eel)
 2:maguro (tuna)
 3:ika (squid)
 4:uni (sea urchin)
 5:tako (octopus)
 6:ikura (salmon roe)
 7:tamago (egg)
 8:toro (fatty tuna)
 9:ama-ebi (AMA shrimp)
10:hotate-gai (scallop)
11:tai (sea bream)
12:aka-gai (ark shell)
13:hamachi (young yellowtail)
14:awabi (abalone)
15:sake (salmon)
16:kazunoko (herring roe)
17:shako (squilla)
18:saba (mackerel)
19:chu-toro (mildly-fatty tuna)
20:hirame (flatfish)
21:aji (horse mackerel)
22:kani (crab)
23:kohada (medium-sized KONOSHIRO gizzard shad)
24:tori-gai (TORI-clam)
25:unagi (eel)
26:tekka-maki (tuna roll)
27:kanpachi (amberjack)
28:miru-gai (MIRU-clam)
29:kappa-roll (cucumber-roll)
30:geso (squid feet)
31:katsuo (oceanic bonito)
32:iwashi (sardine)
33:hokki-gai (HOKKI-clam)
34:shimaaji (hardtail)
35:kani-miso (crab liver)
36:engawa (flesh from around the base of the dorsal and ventral fins of a flounder or flatfish)
37:negi-toro (fatty flesh of tuna minced to a paste and mixed with chopped green leaves of Welsh onions)
38:nattou-maki (fermented bean roll)
39:sayori (halfbeak)
40:takuwan-roll (DAIKON pickles roll)
41:botan-ebi (BOTAN shrimp)
42:tobiko (flying fish roe)
43:inari-zushi (see http://en.wikipedia.org/wiki/Sushi)
44:mentaiko (chili cod roe)
45:sarada (salad)
46:suzuki (sea bass)
47:taraba-gani (king crab)
48:ume&shiso roll (pickled plum & perilla leaf roll)
49:komochi-konbu (herring roe & sea tangle)
50:tarako (cod roe)
51:sazae (turban shell)
52:aoyagi (meat of a trough shell)
53:toro-salmon (fatty tuna & salmon)
54:sanma (Pacific saury)
55:hamo (pike conger)
56:nasu (egg plant)
57:shirauo (Japanese icefish)
58:nattou (fermented bean)
59:ankimo (angler liver)
60:kanpyo-maki (pickled gourd-roll)
61:negi-toro roll (roll style of no.37)
62:gyu-sashi (raw beef)
63:hamaguri (clam)
64:ba-sashi (raw horse meat)
65:fugu (blowfish)
66:tsubu-gai (TSUBU-shell)
67:ana-kyu-maki (sea eel & cucumber roll)
68:hira-gai(=tairagi) (pen shell)
69:okura (gumbo)
70:ume-roll (pickled plum roll)
71:sarada-maki (salad roll)
72:mentaiko-maki (chili cod roe roll)
73:buri (yellowtail)
74:shiso-maki (perilla leaf roll)
75:ika-nattou (squid & fermented bean)
76:zuke (tuna pickled in soy sauce)
77:himo {part of clam}
78:kaiware (DAIKON radish sprouts)
79:kuruma-ebi (prawn)
80:mekabu {part of tangle}
81:kue {kind of cabrilla}
82:sawara (Japanese Spanish mackerel)
83:sasami {kind of raw chicken)
84:kujira (whale)
85:kamo (wild duck)
86:himo {part of clam & cucumber roll)
87:tobiuo (flying fish)
88:ishigaki-dai (ishigaki sea bream)
89:mamakari (Japanese scaled sardine)
90:hoya (ascidian)
91:battera (OSHIZUSHI style mackerel)
92:kyabia (caviar)
93:karasumi (dried mullet roe)
94:uni-kurage (sea urchin & jellyfish)
95:karei (flounder)
96:hiramasa {something like amberjack}
97:namako (sea cucumber)
98:shishamo (smelt)
99:kaki (oyster)

###  sushi3a.5000.10.order / sushi3b.5000.10.order ################

Response orders that represent the preference order in SUSHIs in the item set A and B
These are acquired by a ranking method

- header part: the first line
 <|X*|><sp>1<nl>

|X*|=the number of different kinds of SUSHI. This value is 10 and 100 in files the sushi3a.5000.10.order and the sushi3b.5000.10.order, respectively.

- body of data: after the second line
each line corresponds the order responded by the user in the corresponding line of the file sushi3.udata
 0<sp><|Xi|><sp><1st item ID>....<|Xi|-th item ID><nl>

|Xi|=the length of orders

The top item is the most preferred one

<sp>:space (0x20)
<nl>:new line (0x0a)

### sushi3b.5000.10.score #########################################

- matrix style data separated by <sp>
- each row corresponds to the user in the corresponding line of the file sushi3.udata
- each column corresponds to the SUSHI in the item set B
- using five-point-scale, 0:the most disliked, 4:the most preferred, -1:not rated

### sushi3b.idata #################################################

see also http://en.wikipedia.org/wiki/Sushi

matrix style format separated by TAB

* the row number corresponds to IDs of item set B
* each column represents the following feature
1. item ID
2. name (in UTF-8 encoded Japanese)
3. style         0:maki      1:otherwise (see Wikipedia)
4. major group   0:seafood   1:otherwise
   0 corresponds to the minor group nos 0--8.
5. minor group
    0:aomono (blue-skinned fish)
    1:akami (red meat fish)
    2:shiromi (white-meat fish)
    3:tare (something like baste; for eel or sea eel)
    4:clam or shell
    5:squid or octopus
    6:shrimp or crab
    7:roe
    8:other seafood
    9:egg
   10:meat other than fish
   11:vegetables
6. the heaviness/oiliness in taste, range[0-4] 0:heavy/oily
7. how frequently the user eats the SUSHI, range[0-3] 3:frequently eat
8. normalized price
9. how frequently the SUSHI is sold in sushi shop, range[0-1] 1:the most frequently

- features 3-5 are categorical, and features 6-9 are numerical


### sushi3b.udata #################################################

matrix style format separated by TAB

* each row represents one user that corresponds to the line in the .order or .score file
* each column represents the following feature
1.  the user ID
2.  gender   0:male 1:female
3.  age      0:15-19  1:20-29   2:30-39   3:40-49   4:50-59    5:60-
4.  the total time need to fill questionnaire form
5.  prefecture ID at which you have been the most longly lived until 15 years old
6.  region ID at which you have been the most longly lived until 15 years old
7.  east/west ID at which you have been the most longly lived until 15 years old
8.  prefecture ID at which you currently live
9.  regional ID at which you currently live
10. east/west ID at which you currently live
11. 0 if features 5 and 8 are equal; 1 otherwise

* the user ID
0: Toshihiro Kamishima
1: Shotaro Akaho
2: Hideto Kazawa
3: Jun Fujiki
the rest of users are anonymous

* prefecture no

see also http://en.wikipedia.org/wiki/Prefectures_of_Japan

 0:Hokkaido              1:Aomori                2:Iwate
 3:Akita                 4:Miyagi                5:Yamagata
 6:Fukushima             7:Niigata               8:Ibaraki
 9:Tochigi              10:Gunma                11:Saitama
12:Chiba                13:Tokyo                14:Kanagawa
15:Yamanashi            16:Shizuoka             17:Nagano
18:Aichi                19:Gifu                 20:Toyama
21:Ishikawa             22:Fukui                23:Shiga
24:Mie                  25:Kyoto                26:Osaka
27:Nara                 28:Wakayama             29:Hyogo
30:Okayama              31:Hiroshima            32:Tottori
33:Shimane              34:Yamaguchi            35:Ehime
36:Kagawa               37:Tokushima            38:Kochi
39:Fukuoka              40:Nagasaki             41:Saga
42:Kumamoto             43:Kagoshima            44:Miyazaki
45:Oita                 46:Okinawa              47:foreign countries

* region no
                      prefecture nos
 0:Hokkaido          { 0 }
 1:Tohoku            { 1, 2, 3, 4, 5, 6 }
 2:Hokuriku          { 7, 20, 21, 22 }
 3:Kanto+Shizuoka    { 8, 9, 10, 11, 12, 13, 14, 16 }
 4:Nagano+Yamanashi  { 15, 17 }
 5:Chukyo            { 18, 19, 24 }
 6:Kinki             { 23, 25, 26, 27, 28, 29 }
 7:Chugoku           { 30, 31, 32, 33, 34 }
 8:Shikoku           { 35, 36, 37, 38 }
 9:Kyushu            { 39, 40, 41, 42, 43, 44, 45 }
10:Okinawa           { 46 }
11:Forign            { 47 }

* east/west ID

0 Eastern Japan (region no is smaller or equal to 5)
1 Western Japan (region no is larger or equal to 6)


### Japanese food culture #########################################

To help to analyze this data set, we briefly explain Japanese food culture.

Historically, western Japan has been mainly affected by the culture of the MIKADO emperor and nobles, while eastern Japan has been a home of the SHOGUN and SAMURAI worriers. Therefore, the preference patterns in food are clearly different between these two regions.
- Generally speaking, the eastern Japanese prefers more oily and more heavily seasoned food than the western Japanese.
- The western prefers to UDON noodle, while the eastern loves SOBA noodle.
  http://en.wikipedia.org/wiki/Udon
  http://en.wikipedia.org/wiki/Soba
- The way of cooking Kabayaki, grilled eels, is clearly different.
  http://en.wikipedia.org/wiki/Kabayaki

The other preference patterns depending on regions are:
- The SUSHI in Tokyo is specially called Edomaezushi. The typical examples of the Edomae are: anago (ID:1), zuke (ID:76), and kohada (ID:23).
- A nattou (fermented bean) is loved in the Ibaraki prefecture, but is hated in the Kinki region.
- An oceanic bonito is frequently eaten in the Kochi prefecture.
- A mentaiko (chili cod roe) is a noted product in the Fukuoka prefecture.
- A karasumi (dried mullet roe) is a noted product in the Nagasaki prefecture.
- A batttera sushi is mainly eaten in the Kinki region.
