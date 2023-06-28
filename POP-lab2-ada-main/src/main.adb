with Ada.Text_IO; use Ada.Text_IO;

procedure main is


   dim : constant integer := 100000;
   thread_num : constant integer := 5;

   arr : array(1..dim) of integer;

   procedure Init_Arr is
   begin
      for i in 1..dim loop
         arr(i) := i;
      end loop;
      arr(15):=-15;
   end Init_Arr;

   function part_min_index(start_index, finish_index : in integer) return integer is
      min_index: integer :=1;
   begin
      for i in start_index..finish_index loop

      if arr(i) < arr(min_index) then
        min_index:=i;
      end if;
      end loop;
      return min_index;
   end part_min_index;

   task type starter_thread is
      entry start(start_index, finish_index : in integer);
   end starter_thread;

   protected part_manager is
      procedure set_part_min_index(min_index : in integer);
      entry get_min_index(min_index : out integer);
   private
      tasks_count : integer := 0;
      min1_index: integer :=1;
   end part_manager;

   protected body part_manager is
      procedure set_part_min_index(min_index : in integer) is
      begin
         if arr(min_index) < arr(min1_index) then
             min1_index:=min_index;
         end if;

         tasks_count := tasks_count + 1;
      end set_part_min_index;

      entry get_min_index(min_index : out integer) when tasks_count = thread_num is
      begin
         min_index := min1_index;
      end get_min_index;

   end part_manager;

   task body starter_thread is
      min_index : integer := 1;
      start_index, finish_index : integer;
   begin
      accept start(start_index, finish_index : in integer) do
         starter_thread.start_index := start_index;
         starter_thread.finish_index := finish_index;
      end start;
      min_index := part_min_index(start_index  => start_index,
                      finish_index => finish_index);
      part_manager.set_part_min_index(min_index);
   end starter_thread;

   function parallel_min_index return integer is
      min_index: integer:=1;
      thread : array(1..thread_num) of starter_thread;
      part_arr:integer:=dim/thread_num;

   begin
        for i in 1..thread_num loop
         thread(i).start(part_arr*(i-1)+1,part_arr*i);
      end loop;
      part_manager.get_min_index(min_index);
      return min_index;
   end parallel_min_index;

    min_part_res:integer;
    min_parallel_res:integer;
begin
   Init_Arr;

   min_part_res:=part_min_index(1,dim);
   min_parallel_res:=parallel_min_index;

   Put_Line("Elem:"&arr(min_part_res)'img&" Index:"&min_part_res'img);
   Put_Line("Elem:"&arr(min_parallel_res)'img&" Index:"&min_parallel_res'img);

end main;
