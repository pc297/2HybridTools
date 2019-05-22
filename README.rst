======================================================================================================================
2HybridTools, a handy software to facilitate clone identification and mutation mapping from yeast two-hybrid screening
======================================================================================================================







Introduction
------------

2HybridTools is a GUI-based tool dedicated to yeast two-hybrid (Y2H) and reverse two-hybrid (RY2H) analysis. It uses cDNA sequences from Y2H FASTA files as input. It allows standard Y2H and reverse two-hybrid (RY2H) analysis. It performs translation of Y2H/RY2H sequencing products, following trimming of vector and low-quality sequences, ensuring correct reading frame usage for built-in Blast! or external protein identification of clones. 2HybridTools further allows reconstruction of sequencing products from both ends by performing local pairwise Smith-Waterman alignment. Finally, for RY2H analyses, TwoHybridTools provides detailed mutation reports and heatmaps alongside the bait of interest.

Installation
------------

2HybridTools requires Java ≥1.5. As a portable Java application, it can be installed anywhere on a hard drive, provided that BioJava ≥1.5 (which is distributed with 2HybridTools) is installed in the system classpath (i.e. the lib folder). Alternatively, BioJava is available at https://biojava.org/.

Internet set-up
---------------

The built-iun Blast! feature and reference model loading via accession number require an internet connection. If needed, a proxy server can be set for the latter in the "Options/Network Options" drop-down menu.

Quick usage guidelines
----------------------

2HybridTools can be started by double-clicking the file "2HybridTools.jar" or by entering the following command:

java [-Xmx1536m] -jar 2HybridTools.jar [-Djava.library.path <path_to_biojava, default:"lib/" in 2HybridTools folder]

To load a file, open the File menu and choose "Open" or press Ctrl+O.

A set of sequences is searched for a vector upon loading. Press "Load Vector" to use a DNA or protein tag sequence other than the default one (STHAS). 

Overlapping of larger N and C terminus sequences is possible by checking the "Overlap" checkbox, and a 3' vector can be loaded in a similar way.

Automatic ORF mapping can optionally be performed by pressing the "Find ORFs" button, which will identify and set the longest ORF for each sequence.

To enable RY2H mode, a reference model can loaded as a DNA or protein FASTA file, or alternatively by inputting a RefSeq DNA or protein accession number (this will load the reference sequence directly from NCBI, which requires an internet connection). Any loaded model will be applied for further alignment and mutation collection and plotting ("Graph" button)

To start the analysis, simply hit the "Go" button to process the desired sequence. 

A graphical overview of the mutations plotted against a loaded model can be obtained for the translated sequence by pressing the "Graph" button. A detailed summary of mutations per residue can be obtained by pressing the "Summary" button once the analysis is complete. Detailed mutation summary reports can be saved using the "Save" file menu from the summary window.

Prey proteins can be identified automatically via ExPASy Blast! using the "Blast" button, and optionally sorted alphabetically. Please note that this feature requires an internet connection.

Analysis results can be exported as text files using the "Save DNA Sequences" or "Save Proteins" menu.

Here are a few tips to use this program correctly and efficiently:

* Sequence code, reading frames, translation direction and translation can only be set once a valid file has been loaded

* If the user chooses to modify the sequence code, reading frame, or direction once the sequence has been translated by pressing the "Go" button

* The sequence must be translated once again in order to obtain a graphical overview if different settings are entered.

Support
-------

For troubleshooting, please send an email to `cauchy@ie-freiburg.mpg.de` and I'll try to help you. Please also let me know if you notice any bugs, and also please notify any on github. 

Contributions
-------------

Any contributions are highly welcome and appreciated!


License
-------

Copyright (C) 2007 Pierre Cauchy. This work is licensed under the GNU General Public License v3.0, see ``LICENCE.TXT`` for details. If you require the use of this software under a difference license, please let me know.
