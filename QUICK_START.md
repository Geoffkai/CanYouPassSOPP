# Quiz Game - Quick Reference

## 🚀 Quick Start (3 Steps)

### 1. Build the JAR
```powershell
./build-full.ps1
```

### 2. Create the EXE
- Download Launch4j: https://launch4j.sourceforge.net/
- Open Launch4j GUI
- Output: `QuizGame.exe`
- Jar: `QuizGame.jar`
- Min JRE: `17.0.0`
- Click build

### 3. Create Submission ZIPs
```powershell
./create-submission.ps1
```

## 📁 What You Get

### Files Created:
- ✅ `QuizGame.jar` (~48 MB) - Executable JAR
- ✅ `QuizGame.exe` - Windows executable (after Launch4j)
- ✅ `QuizGame-SourceCode.zip` - Source code submission
- ✅ `QuizGame-Standalone.zip` - Executable submission

### Configuration Files:
- ✅ `launch4j-config.xml` - Launch4j configuration
- ✅ `build-full.ps1` - Build script
- ✅ `create-submission.ps1` - Submission packager

## ✅ Testing Checklist (Quick Version)

### Must Test:
1. [ ] Game launches
2. [ ] Both categories work (Theoretical & Programming)
3. [ ] All 10 questions load
4. [ ] Debug tools work
5. [ ] Scoring correct
6. [ ] Game completes
7. [ ] No crashes

## 📊 Grading Rubric

- **Functionality**: 60% - Game works, no bugs
- **Readability**: 10% - Clean code
- **UI Design**: 10% - Professional look
- **Documentation**: 20% - Complete docs

## 🔧 Commands

### Build:
```powershell
./build-full.ps1
```

### Test JAR:
```powershell
java -jar QuizGame.jar
```

### Test EXE:
```powershell
.\QuizGame.exe
```

### Create Submissions:
```powershell
./create-submission.ps1
```

### Clean:
```powershell
Remove-Item -Recurse -Force out
```

## 📦 What to Submit

1. **QuizGame-SourceCode.zip**
   - Contains: Source code, build files, JSON, documentation
   
2. **QuizGame-Standalone.zip**
   - Contains: EXE (or JAR), images, JSON, user instructions

3. **Report** (if required separately)
   - Team info, features, challenges, screenshots

## 🐛 Common Issues

| Problem | Solution |
|---------|----------|
| JAR won't build | Use `./build-full.ps1` |
| EXE won't start | Check Java 17+ installed |
| Images missing | Copy `src/img` folder |
| Compile errors | Ensure all .java files present |

## 📞 Troubleshooting

If you encounter issues:
1. Make sure Java 17+ is installed: `java -version`
2. Verify all source files are present in `src/` folder
3. Check that JSON files exist: `programming.json`, `theoretical.json`
4. Test thoroughly before submitting!

## ⚠️ Important Notes

- **Java 17+** required
- **Test both ZIP files** before submitting
- **No absolute paths** in code
- **All resources** must be included
- **Programs with bugs** will be returned

## 🎯 Success Criteria

✅ Game runs without errors  
✅ All features work  
✅ Code is clean and commented  
✅ UI looks professional  
✅ Documentation is complete  
✅ Both ZIPs are tested

---

## Ready to Submit?

1. ✅ Built JAR successfully
2. ✅ Created EXE (or have JAR ready)
3. ✅ Tested thoroughly
4. ✅ Created submission ZIPs
5. ✅ Verified both ZIPs work
6. ✅ Documentation complete

**If all checked, you're ready! Good luck! 🎉**

---

All instructions are in this file - you're reading it!
