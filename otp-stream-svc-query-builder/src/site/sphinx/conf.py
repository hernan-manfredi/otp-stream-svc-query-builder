# Configuration file for the Sphinx documentation builder.
#
# For the full list of built-in configuration values, see the documentation:
# https://www.sphinx-doc.org/en/master/usage/configuration.html

import sys,os

# -- Project information -----------------------------------------------------
# https://www.sphinx-doc.org/en/master/usage/configuration.html#project-information

project = 'Universal Processor'
copyright = '2022, Octo Telematics S.P.A.'
author = 'Octo Telematics S.P.A.'
release = '1.0.0-SNAPSHOT'

# -- General configuration ---------------------------------------------------
# https://www.sphinx-doc.org/en/master/usage/configuration.html#general-configuration

extensions = [
        'myst_parser',
        'sphinxcontrib.plantuml'
        ]

templates_path = ['_templates']

exclude_patterns = ['_build', 'Thumbs.db', '.DS_Store']

source_suffix = {
    '.rst': 'restructuredtext',
    '.txt': 'markdown',
    '.md': 'markdown',
}

# -- Options for HTML output -------------------------------------------------
# https://www.sphinx-doc.org/en/master/usage/configuration.html#options-for-html-output

html_theme = 'furo'
html_static_path = ['_static']

latex_elements = {
    'extraclassoptions': 'openany,oneside',
    'printindex': r'\footnotesize\raggedright\printindex',
    'pointsize': '11pt',
    #'preamble': mypreamble,
    #'maketitle': latex_maketitle,
    'fncychap': ''
}

latex_show_urls = 'footnote'

latex_documents = [('index', 'handbook.tex', project, author, 'howto')]
