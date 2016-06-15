byte[] memory = new byte[1000]
int pointer = 0

memory[pointer]+=2
while (memory[pointer] != 0) {

if (pointer < memory.length - 1) pointer+=1
memory[pointer]+=1
if (pointer > 0) pointer-=1
memory[pointer]-=1
}

