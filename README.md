# Luke-RNAwalker
RNA sequences differencing and patching tool

## Tool Authors
JB , AK and RK

# Getting it to Work

Dependencies:

1. `javafx-sdk-16` 
2. `jfoenix`

In intellij IDE add these two libraries by navigating to `project-structure` -> `Libraries` -> then add the above mentioned libraries( for jfoenix just select the jar, for javafx select the lib folder)

then make sure these libraries are attached to the module in the `Modules` Tab

To make a `Run-configuration` go to `Run` -> `Edit Configurations` -> then add a configuration of type `Application`.
In the `main Class` field browse for the `Starter` Class. Then under `VM` arguments add the info provided below ( replace Location with the Path ) 

```
--module-path
YOUR_LOCATION/javafx-sdk-16/lib
--add-modules
javafx.controls,javafx.fxml
--add-exports
javafx.controls/com.sun.javafx.scene.control.behavior=ALL-UNNAMED
--add-exports
javafx.controls/com.sun.javafx.scene.control=ALL-UNNAMED
--add-exports
javafx.graphics/com.sun.javafx.scene=ALL-UNNAMED
```

# Intelligent Data Processing and Applications

## Project Proposal Part I

## RNA Sequences Differencing and Patching Tool

1. Title: RNA Sequences Differencing and Patching Tool.
2. Objective: The main objective of this project is to implement a RNA sequences differencing (edit
   distance) and patching tool, which can be applied on different kinds of RNA sequence formats (such as
   FASTQ, EMBL, FASTA, GCG, etc.).
3. Background and Motivations: RNA sequencing is the process of determining the order of nucleotides
   within an RNA molecule. It allows to determine the order of nucleotide bases, namely: guanine (G), uracil
   (U), adenine (A), and cytosine (C), in a strand of RNA. The advent of rapid RNA sequencing methods has
   greatly accelerated biological and medical research and discovery. Knowledge of RNA sequences has
   become indispensable for basic biological research, and in numerous applied fields such as medical
   diagnosis, biotechnology, virology, and biological systematics^1. Specifically, RNA sequencing facilitates
   the ability to look at alternative gene spliced transcripts, post-transcriptional modifications, gene fusion,
   mutations and changes in gene expression over time, or differences in gene expression in different groups
   or treatments.^ The rapid speed of RNA sequence processing coined with modern RNA sequencing
   technology has been instrumental in the identification of complete RNA sequences of numerous types
   genes. For instance, GenBank (https://www.ncbi.nlm.nih.gov/guide/dna-rna/) comprises the DNA
   DataBank of Japan (DDBJ), the European Molecular Biology Laboratory (EMBL), and GenBank at NCBI.,
   among others. Part of their genome sequence database has been improved by incorporating XML and
   noSQL technologies to allow performing more sophisticated database search functionalities. Other
   databases include miRAN (http://www.mirbase.org/index.shtml), RNAvLab (https://rnavlab.utep.edu/), and
   piRNA (http://regulatoryrna.org/database/piRNA/). Other XML schemas like seqXML and orthoXML
   (https://seqxml.org/xml/Main.html) have also been introduced to help organize and standardize RNA and
   related datasets.
   For instance, Figure 1 shows a sample XML document describing a RNA sequence. Each RNA
   element consists of four sub-elements: accession serves as a unique identifier for the sequence, description
   provides a textual description of the target organism, length provides the length of the sequence in number
   of nucleotide bases, and sequence provides the actual sequence of nucleotide bases.

```
<?XML version= “1.0” ... ?>
<RNADataBank>
<RNA>
<accession>AB000263</accession>
<description>Homo sapiens mRNA for prepro cortistatin like peptide, complete cds. <description>
<length> 207 <length>
<sequence>
ACAAGAUGCCAUUGUCCCCCGGCCUCCUGCUGCUGCUGCUCUCGGGGCCACGGCCACCGCUGCCCUGCC
CCUGGAGGGUGGCCCCACCGGCCGAGACAGCGAGCAUACAGGAAGCGGCAGGAAUAAGGAAAAGCAGC
CUGCAGGAACUUCUUCUGGAAGACCUUCUCCUCCUGCAAAUAAAACCUCACCCAUGAAUGCUCACGCAAG
</sequence>
</RNA>
<DNA>... </DNA>
< /DNADataBank>
```
Fig. 1. Sample XML-based document describing a DNA sequence.
A very famous application of RNA related research is the recent Pfizer BioNTech COVID-19 vaccine: a massager RNA (mRMA)
vaccine that is made of chemically and enzymatically produced components from naturally occurring protein substances.


In addition to the four nucleotide bases mentioned above (e.g., G, U, A, and C), experts utilize
additional symbols to represent ambiguity in describing an RNA sequence [ 3 ], namely:

```
- R represents G or A
- M represents A or C
- S represents G or C
- V represents G, A, or C
- N represents G, U, A, or C. In other words, N basically represents any canonical nucleotide base.
```

For instance, RNA sequence extract AGRGA can represent either: AGGGA, or AGAGA, since symbol
R can stand for either: G or A. In other words, there’s a 1/2 probability that AGRGA stands for AGGGA
and a 1/ 2 probability that it stands for AGAGA. Another example is sequence AGVGA which either stands
for AGGGA, AGAGA, or AGCGA, each with a 1/3 probability since symbol V can represent either G, A,
or C.

4. Project Description: The project can be divided in two main parts: i) RNA sequence differencing, and
   ii) RNA sequence patching.

First, the tool should accept as input two RNA sequences formatted following the sample XML format
in Fig. 1. It should then extract the actual RNA sequence from the sequence element of each XML
document. Then, edit distance has to be computed between the sequences (Fig. 1.a), by adapting an edit
distance algorithm provided in the literature [ 9 ], namely the Wagner-Fisher algorithm [ 10 ] designed to
compare sequences of characters. Note that ED computations need to “intelligently” consider nucleotide
bases as well as ambiguity symbols in comparing the sequences. Subsequently, edit script (diff) extraction
is undertaken, where the produced diffs have to be outputted in a useful XML-based format. For instance,
and edit script ES(S1, S2) = Ins(S 1 [2], 1, S 2 [2]), Upd(S 1 [3], _‘_ G _’_ ) should be transformed and represented in
XML to be stored in a separate XML document for later usage in patching. Here, the students can come up
with their own XML syntax to describe diffs, or they can adapt existing XML diff representations such as:

- DeltaXML [ 6 ], and XyDiff [ 1 ] which purposefully include additional redundant information so that
  the diffs follow the same topological structure of their source documents to simplify querying,
- DUL [ 7 ] and EDUL [ 4 ] which include additional context information (concerning the siblings and
  parents of the nodes affected by each edit operation), to generate diff descriptions which would be
  independent of the document trees based on which they were generated (to be patched with any
  arbitrary document tree), or
- SDL [ 8 ] which is designed for human readability and compactness (originally used in Web service
  multicasting).

After identifying the edit script (diff) transforming one RNA sequence into another, a complementary
functionality would be to apply the diff to either one of the source RNA sequences, in order to obtain the
other sequence (Fig. 2.b). This action is known as patching.

While various tools have been developed for edit distance computations (differencing), very few
implementations enable patching. A main aspect here is the diff output format itself, and how well
formatted diffs should be handled in order to re-integrate the changes in the original sequences.

5. Expected Results: The main objective of this project is to develop and implement the RNA sequence
   differencing and patching modules described above. Each student group is expected to conceive and
   implement:
    - An adapted edit distance algorithm, which takes as input two RNA sequences (read from two
      XML-based documents), and outputs their distance (similarity) value,
    - An edit script extraction algorithm, which extracts the edit script describing the transformation
      operations between two RNA sequences, based on their tree edit distance computation,
    - A sequence patching algorithm, which takes as input the RNA sequence and edit script, and
      applies the edit script to the input sequence in order to obtain the other sequence.

## Project Proposal Part II

## RNA Sequences Similarity-based Search using Set-based Vector-based Models

1. Title: RNA Sequences Comparison using Set-based and Vector-based Models
2. Objective: The objective of this project is three fold: i) implement a RNA sequences comparison tool,
   which computes the similarity between two RNA sequences using set-based and vector-based IR models,
   considering the different variations and similarity measures that can be used with every model, ii) compare
   the IR-based models’ time performance and quality with project #1’s ED-based solution, iii) implement a
   search component allowing to accept as input an RNA sequence, and to produce as output the list of most
   similar RNA sequences in a given dataset, ranked based on their similarities with the query. The RNA
   search component should support the ED and IR-based methods mentioned above.

2. Project Description: The project can be divided in six main parts: i) RNA sequence pre-processing, ii)
   RNA set and vector representations, iii) symbol weighting, iv) RNA set and vector similarity evaluation, v)
   comparison with project #1’s ED measure, iv) RNA similarity-based search function.
   First, the tool should accept as input two RNA sequences (similar to Project #1). The sequences need
   to be processed for tokenization to dissect every sequence into individual nucleotide symbols. These will be
   utilized in the following phase to produce the set-based and vector-based representations, following the
   user’s choice^1. Regarding the vector model, TF-IDF weights will then be computed before comparing the
   produced vector using any typical vector based similarity measure (e.g., cosine or PCC). Set-based
   similarity measures will be used with the set-based model (e.g., Jaccard or Dice). Note that the user should
   be able to fine-tune TF-IDF weighting (activate TF only, IDF only, or both) as well as the set and vector
   similarity measures following her preferences and needs. In addition, a special graphical interface should be
   included to allow invoking and comparing the present IR-based solution with project #1’s ED-based
   solution, in terms of:

- Time performance: comparing pre-processing time (to produce sequence, set, and vector
  representations), and similarity evaluation time,
- Quality: ability to detect more/less similarities, highlighting the strong and weak points of every
  solution

Finally, a similarity-based search component should be implemented, allowing the system to accept as
input an RNA sequence, and to produce as output the list of most similar RNA sequences in a given
dataset, ranked based on their similarities with the query. The RNA search component should support the
ED and IR-based methods mentioned above, and should be tunable following the user’s needs.

5. Expected Results: The main objective of this project is to develop and implement IR-based similarity
   evaluation approaches to compare RNA sequences, and allow the user to fine-tune their behavior following
   her needs, and compare them with Project #1’s ED-based solution. Each student group is expected to
   conceive and implement:
   
- A pre-processing module to transform input RNA sequences into set-based and vector-based
  representations,
- Weighting and comparing the RNA sets and vectors to produce similarity values,
- Comparing the implemented solutions with Project #1’s ED-based measure, (after fixing all issues
  and bugs in the latter, if any).
- Producing a similarity-based search component based on the above mentioned solutions.



