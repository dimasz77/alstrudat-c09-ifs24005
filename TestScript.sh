#!/bin/bash

# Jangan berhenti jika ada error agar bisa menghitung skor akhir
set +e

# 1. Compile Java program dengan Maven
echo "🔨 Compiling Java program..."
if ! mvn clean compile; then
  echo "❌ Build failed! Exiting..."
  exit 1
fi
echo "✅ Build successful!"

# 2. Pastikan file weights.txt ada
if [ ! -f testcases/weights.txt ]; then
  echo "❌ Error: File testcases/weights.txt tidak ditemukan!"
  exit 1
fi

total_score=0
index=1

# 3. Baca bobot
while read -r line || [ -n "$line" ]; do
  [[ -z "$line" ]] && continue
  weight=$(echo "$line" | grep -oE '[0-9]+$' | tail -n 1)

  echo "|--------------------------------------------------|"
  echo "Test Case $index"
  
  input_file="testcases/input$index.txt"
  expected_file="testcases/expected$index.txt"
  output_file="testcases/output$index.txt"

  if [ ! -f "$input_file" ] || [ ! -f "$expected_file" ]; then
    index=$((index + 1))
    continue
  fi

  echo "🚀 Running test case $index (Weight: $weight%)..."

  # --- PERUBAHAN DI SINI ---
  # Kita jalankan langsung dari target/classes agar tidak bingung mencari nama JAR
  java -cp target/classes del.alstrudat.App < "$input_file" > "$output_file"

  echo "🔍 Actual Output:"
  cat "$output_file"
  echo "🔍 Expected Output:"
  cat "$expected_file"

  if diff -Z -q "$output_file" "$expected_file" > /dev/null; then
    echo "✅ Test case $index PASSED! (+$weight%)"
    total_score=$((total_score + weight))
  else
    echo "❌ Test case $index FAILED!"
  fi

  index=$((index + 1))
done < testcases/weights.txt

echo "|--------------------------------------------------|"
echo "🎯 Final Score: $total_score%"

if [ "$total_score" -lt 100 ]; then
  exit 1
else
  exit 0
fi