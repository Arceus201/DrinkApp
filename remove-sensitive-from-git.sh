#!/bin/bash

# Script to remove sensitive information from Git history
# This will rewrite Git history to remove real credentials from gradle.properties

echo "⚠️  WARNING: This will rewrite Git history!"
echo "⚠️  Make sure you have a backup before proceeding."
echo ""
read -p "Do you want to continue? (yes/no): " confirm

if [ "$confirm" != "yes" ]; then
    echo "Aborted."
    exit 0
fi

echo ""
echo "🔄 Removing sensitive data from Git history..."
echo ""

# Method 1: Using git filter-branch (works on older Git versions)
echo "Using git filter-branch..."
git filter-branch --force --index-filter \
  "git rm --cached --ignore-unmatch gradle.properties" \
  --prune-empty --tag-name-filter cat -- --all

echo ""
echo "✅ Git history has been rewritten!"
echo ""
echo "📝 Next steps:"
echo "1. Verify the changes: git log --all --oneline"
echo "2. Force push to remote (if needed): git push origin --force --all"
echo "3. Force push tags (if needed): git push origin --force --tags"
echo ""
echo "⚠️  IMPORTANT: All team members must re-clone the repository!"
echo ""
