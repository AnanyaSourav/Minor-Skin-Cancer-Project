# Minor-Skin-Cancer-Project
Patient-Specific Correlation Analysis of Gene Expression and Copy Number Alterations in Skin Cancer.
I do not claim any correctness over the generated results.

Overview
This project investigates the relationship between gene expression and copy number alterations (CNA) in skin cancer using patient-wise correlation analysis. The goal is to identify oncogenes and tumor suppressors influenced by CNA changes.

Key Steps
Data Collection:
Patient-wise RNA expression and CNA data were collected (50 patients) from https://www.cancermodels.org/.
Expression values were converted to Z-scores, and CNA values were transformed to log2R_CNA.

Correlation Analysis:
Pearson and Spearman correlations were computed to evaluate relationships between gene expression and CNAs.
Adjusted p-values were used for statistical significance.

Visualization & Summarization:
Patient-wise correlation plots were generated.
Average correlation trends across patients were analyzed.

Results:
Identified key oncogenes and tumor suppressors showing significant correlation patterns.
Demonstrated patient-specific variations in gene regulation due to CNA changes.
