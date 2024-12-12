# 실행 코드 (MacOS 기준)

```
javac **/*.java && java -cp "ojdbc6.jar:." Main
```

- `DBex.java > SQLRunner` 의 각 상수를 알맞게 조절해서 실행
- (Windows에선 `&&` 기호를 못알아볼 거임. 대신 `;` 기호를 쓰거나, 각 명령어를 따로 입력해야할 수도)
- (Eclipse 사용하면 과제 zip 파일에서 설명한 대로 `Add External JAR` 기능 활용할 것)

# 새로 배운 것들

- `JFrame.pack()`: 프레임 내 컨테이너에 맞춰서 프레임의 크기를 맞추어줌
  - https://stackoverflow.com/a/22982334
- `Array.copyOf(T[], len)`

# 오답노트

- `toArray` 사용 시 저장 배열의 크기를 미리 설정해주어야 함. `Vector.size()`로

