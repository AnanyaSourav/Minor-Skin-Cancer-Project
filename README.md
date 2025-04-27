<p align="center">
  <img src="https://img.shields.io/badge/Status-Completed-brightgreen" alt="Project Status" />
  <img src="https://img.shields.io/badge/License-CC BY-NC-ND 4.0-blue" alt="License:CC BY-NC-ND 4.0" />
  <img src="https://img.shields.io/badge/Language-R%20%7C%20Java-orange" alt="Language: R and Java" />
  <img src="https://img.shields.io/badge/Data-Skin%20Cancer%20Patient%20Specific-lightgrey" alt="Skin Cancer Data" />
  <img src="https://img.shields.io/badge/Mathematical_Modeling-Yes-blueviolet" alt="Mathematical Modeling" />
  <img src="https://img.shields.io/badge/Clinical_Application-Potential-red" alt="Clinical Relevance" />
</p>

# Minor-Skin-Cancer-Project
**Title: A Mathematical Framework Based on Stratification of Gene CNA and Expression Variability and Interaction Patterns in Skin Cancer Using Patient-Specific Data**

**Overview**
This project develops a patient-wise mathematical framework to explore gene-level variability in copy number alterations (CNA) and gene expression profiles for skin cancer. Using patient-specific data collected from CancerModel.org, the framework aims to systematically analyze, normalize, model, and stratify genes based on their variability patterns and interaction behavior without using machine learning or external prediction models. The approach combines variance analysis, probabilistic modeling, graph theory, and clustering, establishing a novel mathematical perspective on cancer genomic heterogeneity.

**Project Structure**
1. Data Collection & Preprocessing:
>Patient-wise CNA and expression data (in GISTIC scores/log2R_CNA and TPM/Z-score).
>Java code used for intra-patient standardization and merging based on HGNC symbols.
>Cross-patient normalization of log2R_CNA and Z-score values.

2. Variance Analysis:
>Computation of mean and standard deviation of CNA and expression per gene across patients.
>Categorization of genes based on variability (Low, Moderate, High).

3. Clustering and Graph Construction:
>Hierarchical and k-means clustering of genes based on variance.
>Sparse graph-based networks generated using variability patterns.
>Visualization of gene networks and patient clustering.

4. Mathematical Modeling:
>CNA-Expression Distance Score (CEDS) for gene similarity
​>Graph-theoretical representation and stratification.
>Probabilistic classification of patients and genes based on derived variability metrics.

**Highlights**
>Purely Mathematical Analysis: No machine learning algorithms, focusing instead on mathematical/statistical rigor.
>Patient-Specific Insights: Customized normalization and modeling for small sample sizes (50 skin cancer patients).
>Graph-Based Gene Interaction Modeling: Using sparse adjacency and topological filtering.
>Foundation for Precision Medicine: Approach adaptable for future studies integrating clinical metadata, epigenetics, and larger datasets.

**If you find this framework helpful, please consider citing the project:
"A Mathematical Framework Based on Stratification of Gene CNA and Expression Variability and Interaction Patterns in Skin Cancer Using Patient-Specific Data" (2025).**

## License
This project is licensed under the Creative Commons Attribution-NonCommercial-NoDerivatives 4.0 International License (CC BY-NC-ND 4.0). See the [LICENSE](./LICENSE) file for more information.

