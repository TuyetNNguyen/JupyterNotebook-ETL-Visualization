#!/usr/bin/env python
# coding: utf-8

# # Data Analysis - Celebrity Deaths in 2016
# 
# Source: [Wikipedia - Deaths in 2016](https://en.wikipedia.org/wiki/Deaths_in_2016)
# 
# #### Structure of dataset:
# - File: "celebrity_deaths_2016.xlsx"
# - Contains 2 sheets:
#  - "celeb_death": contains records of deaths of famous humans and non-humans
#  - "cause_of_death": contains the causes of the deaths (you'll need to merge it with the "celeb_death" sheet)
# 
# #### Other information about the dataset:
# - The cause of death was not reported for all individuals
# - The dataset might include deaths that took place in other years (you'll need to ignore these records)
# - The dataset might contain duplicate records (you'll need to remove them)
# 
# #### The goals of the exercise:
# - Load, merge, and clean the data
# - Explore the data and answer some simple questions
# - Run some basic analysis
# - Visualize your results


get_ipython().run_cell_magic('capture', '', '###########################################################\n### EXECUTE THIS CELL TO TEST THE SOLUTIONS ###\n###########################################################\nimport imp, os, sys\nimport importlib\nsol = imp.load_compiled("sol", "./sol.py")\nfrom nose.tools import assert_equal\nfrom pandas.util.testing import assert_frame_equal, assert_series_equal')


import pandas as pd
import matplotlib.pyplot as plt 

get_ipython().run_line_magic('matplotlib', 'inline')


# ### Load, merge, and clean the data

""" 1.
1. Load celebrity_deaths_2016.xlsx file
2. then print it
"""

xl = pd.ExcelFile("celebrity_deaths_2016.xlsx")
print(xl)

##########################
### TEST THE SOLUTION ###
##########################
assert_equal(xl.sheet_names, sol.xl.sheet_names)



""" 2.
1. Read the "celeb_death" sheet into a dataframe named "df"
2. Take a look at the top 5 rows. Save it in a variable called 'top5', then print it
"""

df = xl.parse("celeb_death")
top5 = df.head(5)
top5

##########################
### TEST THE SOLUTION ###
##########################
assert_frame_equal(top5, sol.top5)



""" 3.
1. Take a look at the data types stored in each column in df. Store these in a variable called 'df_dtypes'
2. Get the shape of df. Store this in a variable called 'df_shape'
3. Print these
"""

df_dtypes = df.dtypes
df_shape = df.shape
print(df_dtypes)
print(df_shape)


##########################
### TEST THE SOLUTION ###
##########################

assert_series_equal(df_dtypes, sol.df_dtypes)
assert_equal(df_shape, sol.df_shape)


""" 4.
Drop the duplicates (based on all columns) from df
"""
df = df.drop_duplicates()
df


##########################
### TEST THE SOLUTION ###
##########################

assert_frame_equal(df, sol.df2)



""" 5.
1. Read the "cause_of_death" sheet into a DataFrame named "cause_of_death"
2. Take a look at the top 5 rows. Store this in a variable named cause_top5, then print it
"""

cause_of_death = xl.parse("cause_of_death")
cause_top5 = cause_of_death[:5]
cause_of_death.shape


##########################
### TEST THE SOLUTION ###
##########################

assert_frame_equal(cause_top5, sol.cause_top5)



""" 6.
Drop the duplicates (based on the "cause_id" column) from the cause_of_death DataFrame
"""

cause_of_death.drop_duplicates(
    subset = "cause_id", 
    keep = 'first', 
    inplace = True)

cause_of_death.shape


##########################
### TEST THE SOLUTION ###
##########################

assert_frame_equal(cause_of_death, sol.cause_of_death)


""" 7.
1. Merge the cause_of_death DataFrame with the df DataFrame and name the new DataFrame as "df"
2. Take a look at the top 5 rows in df. Save these in a variable called df_top5, then print it
"""

df = pd.merge(left = df, right = cause_of_death, how = 'left', left_on = 'cause_id', right_on = 'cause_id')
df_top5 = df.head(5)
df_top5


assert_frame_equal(df_top5, sol.df_top5)


# ### Querying data
# 

""" 8.
cast age DataFrame df to a numeric value
"""
df["age"] = pd.to_numeric(df["age"], errors="coerce", downcast ="integer")


##########################
### TEST THE SOLUTION ###
##########################

assert_series_equal(df['age'], sol.df3['age'])



""" 9.
The average age of death? 
"""
avg_age = df["age"].mean()
avg_age


##########################
### TEST THE SOLUTION ###
##########################

assert_equal(avg_age, sol.avg_age)



""" 10.
How many people died after the age of 70?
"""
age_seventy = df["age"] > 70.0
count = len(df[age_seventy])

##########################
### TEST THE SOLUTION ###
##########################
assert_equal(count, sol.count)


""" 11.
Who died the youngest and what was the cause of death?
"""
youngest_death = df["age"] == df["age"].min()
youngest_name = df[youngest_death]["name"]
youngest_cause = df[youngest_death]["cause of death"]


##########################
### TEST THE SOLUTION ###
##########################

assert_series_equal(youngest_name, sol.youngest_name)
assert_series_equal(youngest_cause, sol.youngest_cause)



""" 12.
Cast bio to a string.
"""
df["bio"] = df["bio"].astype(str)


##########################
### TEST THE SOLUTION ###
##########################

assert_series_equal(df['bio'], sol.df['bio'])


""" 13.
How many American celebrities died?
"""
count_american = df["bio"].str.contains("American").sum()


##########################
### TEST THE SOLUTION ###
##########################

assert_equal(count_american, sol.count_american)



rand_int = random.randint(0, len(death_age_fifty) - 1)
rand_cause = death_age_fifty[rand_int:rand_int + 1]
print("Age 50. Cause of Death:", rand_cause)



""" 14.
What was one known cause of death for celebrities who died at age 50?
"""

import random
random.seed(0)

age_fifty = df["age"] == 50
death_age_fifty = df[age_fifty]["cause of death"]
rand_int = random.randint(0, len(death_age_fifty) - 1)
rand_cause = death_age_fifty.iloc[rand_int]
print("Age 50. Cause of Death:", rand_cause)

##########################
### TEST THE SOLUTION ###
##########################

from nose.tools import assert_in
assert_equal(type(rand_cause), str)
assert_in(rand_cause, sol.possible_causes.values)



""" 15.
What was the mean age for each cause of death?
"""
import numpy as np
df_grouped_cause = df.groupby(["cause of death"]).agg([np.mean])["age"]
df_grouped_cause

assert_frame_equal(df_grouped_cause, sol.df_grouped_cause)


# ### Count the number of people who died in each month of 2016


""" 16.
Make a new column in the DataFrame df with the numeric month of death
"""

def get_month(date):
    return date.month

df["month"] = df["date of death"].apply(get_month)


##########################
### TEST THE SOLUTION ###
##########################
assert_series_equal(df['month'], sol.df['month'])



""" 17.
Make a new column in the DataFrame df with the year of death
"""

def get_year(date):
    return date.year
df["year"] = df["date of death"].apply(get_year)


##########################
### TEST THE SOLUTION ###
##########################

assert_series_equal(df['year'], sol.df['year'])



""" 18.
There could be a small number of deaths that didn’t take place in 2016.  Just in case, 
filter out the deaths that didn’t take place in 2016.
"""
bool_year_2016 = df["year"] == 2016
df_2016 = df[bool_year_2016]
df_2016

##########################
### TEST THE SOLUTION ###
##########################
assert_frame_equal(df_2016, sol.df_2016)


""" 19.
Using a pivot table, obtain a list that contains the number of people that died in each month
"""
df_per_month = pd.pivot_table(
    df_2016, index= ["month"],
    values = ["bio"],
    aggfunc = ["count"]
    )
df_per_month


##########################
### TEST THE SOLUTION ###
##########################

df_sub = df_per_month
df_sol = sol.df_per_month

df_sub.columns = ['namelen']
df_sol.columns = ['namelen'] 

assert_frame_equal(df_sub, df_sol)


# ### Data Visualization

# In[520]:


""" 20. 
Visualize the number of deaths per month as a bar chart
"""
df_per_month.plot(
    kind = "bar",
    figsize = (10,5),
    fontsize = 10,
    legend = False,
    title = "Nuber of deaths per month in 2016"
)

plt.xlabel("Month")
plt.ylabel("Total death")

plt.show()



# ### A bar chart that plots the number of deaths per nationality


""" 21.
Create a new column in the DataFrame df that identifies the nationality of each celebrity, 
extracting the first word from the bio
"""

def get_nationality(bio):
    words = bio.split()
    return words[0]

df["nationality"] = df["bio"].apply(get_nationality)

##########################
### TEST THE SOLUTION ###
##########################
assert_series_equal(df['nationality'], sol.df['nationality'])



""" 22.
Make a bar chart that plots the number of deaths per nationality
Only include nationalities with more than 50 deaths
"""
count_unique_by_nation = df["nationality"].value_counts()
unlucky_countries = count_unique_by_nation.loc[lambda x: x>50]

unlucky_countries.plot(
    kind = "bar",
    figsize = (10,5),
    fontsize = 10,
    legend = False,
    title = "Nuber of deaths per country"
)

plt.xlabel("nationalities")
plt.ylabel("Total death")

plt.show()


##########################
### TEST THE SOLUTION ###
##########################

assert_series_equal(unlucky_countries, sol.unlucky_countries)

