byte[] memory = new byte[1000]
int pointer = 0

memory[pointer]+=8
while (memory[pointer] != 0) {

if (pointer < memory.length - 1) pointer+=1
memory[pointer]+=4
while (memory[pointer] != 0) {

if (pointer < memory.length - 1) pointer+=1
memory[pointer]+=2
if (pointer < memory.length - 1) pointer+=1
memory[pointer]+=3
if (pointer < memory.length - 1) pointer+=1
memory[pointer]+=3
if (pointer < memory.length - 1) pointer+=1
memory[pointer]+=1
if (pointer > 3) pointer-=4
memory[pointer]-=1
}

if (pointer < memory.length - 1) pointer+=1
memory[pointer]+=1
if (pointer < memory.length - 1) pointer+=1
memory[pointer]+=1
if (pointer < memory.length - 1) pointer+=1
memory[pointer]-=1
if (pointer < memory.length - 2) pointer+=2
memory[pointer]+=1
while (memory[pointer] != 0) {

if (pointer > 0) pointer-=1
}

if (pointer > 0) pointer-=1
memory[pointer]-=1
}

if (pointer < memory.length - 2) pointer+=2
1.times { print ((char) memory[pointer]) }
if (pointer < memory.length - 1) pointer+=1
memory[pointer]-=1
memory[pointer]-=1
memory[pointer]-=1
1.times { print ((char) memory[pointer]) }
memory[pointer]+=7
2.times { print ((char) memory[pointer]) }
memory[pointer]+=3
1.times { print ((char) memory[pointer]) }
if (pointer < memory.length - 2) pointer+=2
1.times { print ((char) memory[pointer]) }
if (pointer > 0) pointer-=1
memory[pointer]-=1
1.times { print ((char) memory[pointer]) }
if (pointer > 0) pointer-=1
1.times { print ((char) memory[pointer]) }
memory[pointer]+=3
1.times { print ((char) memory[pointer]) }
memory[pointer]-=1
memory[pointer]-=1
memory[pointer]-=1
memory[pointer]-=1
memory[pointer]-=1
memory[pointer]-=1
1.times { print ((char) memory[pointer]) }
memory[pointer]-=1
memory[pointer]-=1
memory[pointer]-=1
memory[pointer]-=1
memory[pointer]-=1
memory[pointer]-=1
memory[pointer]-=1
memory[pointer]-=1
1.times { print ((char) memory[pointer]) }
if (pointer < memory.length - 2) pointer+=2
memory[pointer]+=1
1.times { print ((char) memory[pointer]) }
if (pointer < memory.length - 1) pointer+=1
memory[pointer]+=2
1.times { print ((char) memory[pointer]) }
